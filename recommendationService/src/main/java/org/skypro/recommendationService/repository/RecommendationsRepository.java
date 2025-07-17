package org.skypro.recommendationService.repository;

import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.WithdrawTransaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Репозиторий для проверки условий рекомендаций на основе данных из базы.
 * Использует JdbcTemplate для выполнения сложных SQL-запросов.
 */
@Component
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Инициализация с использованием кастомного JdbcTemplate.
     *
     * @param jdbcTemplate JdbcTemplate, настроенный на базу рекомендаций
     */
    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Проверяет условия для правила A:
     * - Пользователь использует дебетовые продукты
     * - Не использует инвестиционные продукты
     * - Имеет накопления более 1000 по сберегательным счетам
     *
     * @param userId идентификатор пользователя
     * @return true, если все условия выполнены
     */
    public boolean checkUserConditionsA(UUID userId) {
        String sql = "SELECT COUNT(*) FROM (" +
                "SELECT DISTINCT u.id " +
                "FROM users u " +
                "INNER JOIN transactions t ON t.user_id = u.id " +
                "INNER JOIN products p ON p.id = t.product_id " +
                "WHERE u.id = ? AND " +
                "u.id IN ( " +
                "   SELECT t.user_id FROM transactions t INNER JOIN products p ON p.id = t.product_id WHERE p.type = 'DEBIT' " +
                ") AND u.id NOT IN ( " +
                "   SELECT t.user_id FROM transactions t INNER JOIN products p ON p.id = t.product_id WHERE p.type = 'INVEST' " +
                ") AND u.id IN ( " +
                "   SELECT t.user_id FROM transactions t INNER JOIN products p ON p.id = t.product_id WHERE t.type = 'DEPOSIT' AND p.type = 'SAVING' GROUP BY t.user_id HAVING SUM(t.amount) > 1000" +
                ") ) AS sub";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    /**
     * Проверяет условия для правила B:
     * - Пользователь использует дебетовые продукты
     * - Сумма снятий по дебетовым или сберегательным продуктам превышает 50 000
     * - Сумма депозитов превышает сумму снятий
     *
     * @param userId идентификатор пользователя
     * @return true, если условия выполнены
     */
    public boolean checkUserConditionsB(UUID userId) {
        String sql =
                "SELECT COUNT(*) FROM users u " +
                        "WHERE u.id = ? " +
                        "AND EXISTS ( " +
                        "   SELECT 1 FROM transactions t INNER JOIN products p ON p.id = t.product_id " +
                        "   WHERE t.user_id = u.id AND p.type='DEBIT'" +
                        ") " +
                        "AND ( " +
                        "   ( " +
                        "       SELECT COALESCE(SUM(t2.Amount),0) FROM transactions t2 INNER JOIN products p2 ON p2.id=t2.product_id " +
                        "       WHERE t2.user_id = u.id AND p2.type='DEBIT' AND t2.type='WITHDRAW'" +
                        "   ) >= 50000 OR ( " +
                        "       SELECT COALESCE(SUM(t3.Amount),0) FROM transactions t3 INNER JOIN products p3 ON p3.id=t3.product_id " +
                        "       WHERE t3.user_id = u.id AND p3.type='SAVING' AND t3.type='WITHDRAW'" +
                        "   ) >= 50000" +
                        ") " +
                        "AND ( " +
                        "   (SELECT COALESCE(SUM(t4.Amount),0) FROM transactions t4 INNER JOIN products p4 ON p4.id=t4.product_id WHERE t4.user_id = u.id AND p4.type='DEBIT' AND t4.type = 'DEPOSIT') > " +
                        "   (SELECT COALESCE(SUM(t5.Amount),0) FROM transactions t5 INNER JOIN products p5 ON p5.id=t5.product_id WHERE t5.user_id = u.id AND p5.type='DEBIT' AND t5.type='WITHDRAW')" +
                        ")";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    /**
     * Проверяет условия для правила C:
     * - Пользователь не использует кредитные продукты
     * - Имеет положительное сальдо между депозитами и снятиями по дебетовым продуктам
     * - Снял более 100 000 по дебетовым продуктам
     *
     * @param userId идентификатор пользователя
     * @return true, если все условия выполнены
     */
public boolean checkUserConditionsC(UUID userId) {
    String sql =
            "SELECT COUNT(*) FROM ( " +
                    "SELECT DISTINCT u.* " +
                    "FROM users u " +
                    "INNER JOIN transactions t ON t.user_id = u.id " +
                    "INNER JOIN products p ON p.id = t.product_id " +
                    "INNER JOIN ( " +
                    "  SELECT deposit.user_id FROM ( " +
                    "    SELECT SUM(Amount) AS sum_deposit, t.user_id FROM transactions t " +
                    "    INNER JOIN products p ON p.id = t.product_id AND t.type = 'DEPOSIT' AND p.type ='DEBIT' " +
                    "    GROUP BY t.user_id" +
                    "  ) AS deposit INNER JOIN ( " +
                    "    SELECT SUM(Amount) AS sum_withdraw, t.user_id FROM transactions t " +
                    "    INNER JOIN products p ON p.id = t.product_id AND t.type = 'WITHDRAW' AND p.type ='DEBIT' " +
                    "    GROUP BY t.user_id" +
                    "  ) AS withdraw ON deposit.user_id = withdraw.user_id GROUP BY deposit.user_id HAVING (sum_deposit - sum_withdraw) > 0" +
                    ") cte ON cte.user_id = t.user_id WHERE u.id NOT IN (" +
                    "  SELECT t2.user_id FROM transactions t2 INNER JOIN products p2 ON p2.id = t2.product_id WHERE p2.type = 'CREDIT'" +
                    ") AND u.id IN (" +
                    "  SELECT t3.user_id FROM transactions t3 INNER JOIN products p3 ON p3.id = t3.product_id WHERE t3.type='WITHDRAW' AND p3.type='DEBIT' GROUP BY t3.user_id HAVING SUM(t3.Amount) > 100000" +
                    ")" +
                    ") AS subquery WHERE subquery.id = ?";

    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
    return count != null && count > 0;
}

    public int getCountTransactionsByProductName(UUID userId, String product){
        String script = "SELECT COUNT(t.product_id) " +
                "FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND p.type = ?";

        Integer count = jdbcTemplate.queryForObject(script, new Object[]{userId, product}, Integer.class);

        return count;
    }

    @Cacheable(cacheNames = {"depositAmount"},  key = "{#userId}")
    public DepositTransactions getDepositAmountByUserId(UUID userId) {

        String script = "SELECT " +
                "SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS debit_amount, " +
                "SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS saving_amount, " +
                "SUM(CASE WHEN p.type = 'CREDIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS credit_amount, " +
                "SUM(CASE WHEN p.type = 'INVEST' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS invest_amount " +
                "FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ?";

        DepositTransactions depositTransactions = jdbcTemplate.queryForObject(script, (rs, rowNum) ->
                        new DepositTransactions(
                                rs.getInt("debit_amount"),
                                rs.getInt("saving_amount"),
                                rs.getInt("credit_amount"),
                                rs.getInt("invest_amount")),
                userId);
        return depositTransactions;
    }

    @Cacheable(cacheNames = {"withdrawAmount"},  key = "{#userId}")
    public WithdrawTransaction getWithdrawAmountByUserId(UUID userId) {
        String script = "SELECT " +
                "SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS debit_amount, " +
                "SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS saving_amount, " +
                "SUM(CASE WHEN p.type = 'CREDIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS credit_amount, " +
                "SUM(CASE WHEN p.type = 'INVEST' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS invest_amount " +
                "FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ?";
        WithdrawTransaction withdrawTransaction = jdbcTemplate.queryForObject(script, (rs, rowNum) ->
                        new WithdrawTransaction(
                                rs.getInt("debit_amount"),
                                rs.getInt("saving_amount"),
                                rs.getInt("credit_amount"),
                                rs.getInt("invest_amount")),
                userId);
        return withdrawTransaction;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

}
