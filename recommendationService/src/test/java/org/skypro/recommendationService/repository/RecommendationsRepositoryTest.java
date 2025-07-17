package org.skypro.recommendationService.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.recommendationService.model.DepositTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
class RecommendationsRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RecommendationsRepository repository;

    @BeforeEach
    void setup() {
        repository = new RecommendationsRepository(jdbcTemplate);
    }

    @AfterEach
    void cleanup() {
        jdbcTemplate.execute("DELETE FROM transactions");
        jdbcTemplate.execute("DELETE FROM products");
    }

    @Test
    void testGetDepositAmountByUserId() throws Exception {

        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        UUID productId = UUID.randomUUID();


        jdbcTemplate.update("INSERT INTO products(id, type) VALUES (?, 'DEBIT')", productId);
        jdbcTemplate.update("INSERT INTO transactions(product_id, user_id, type, amount) VALUES ( ?, ?, 'DEPOSIT', 100)", productId, userId.toString());

        DepositTransactions result = repository.getDepositAmountByUserId(userId);

        assertEquals(100, result.getDebitAmount(), "Сумма дебета должна быть равна 100");
        assertEquals(0, result.getSavingAmount(), "Сумма сбережений должна быть равна 0");
        assertEquals(0, result.getCreditAmount(), "Сумма кредита должна быть равна 0");
        assertEquals(0, result.getInvestAmount(), "Сумма инвестиций должна быть равна 0");
    }

}