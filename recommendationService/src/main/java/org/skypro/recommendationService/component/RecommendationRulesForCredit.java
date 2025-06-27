//package org.skypro.recommendationService.component;
//
//import org.skypro.recommendationService.dto.RecommendationDto;
//import org.springframework.data.jdbc.repository.query.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//import static org.skypro.recommendationService.model.Constants.*;
//
//@Component
//public abstract class RecommendationRulesForCredit implements RecommendationSetOfRules {
//
//        private final JdbcTemplate jdbcTemplate;
//
//    protected RecommendationRulesForCredit(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public Optional<RecommendationDto> matchesRules(String user_id) {
//        if (addDebitMoreThanSpendDebit(user_id) && noOneProductCredit(user_id) &&  sumSpendDebitMoreOneHundredThousandsRub(user_id) > 100_000) {
//            return Optional.of(new RecommendationDto(productIdCredit, productNameCredit, productTextCredit));
//        } else {
//            return Optional.empty();
//        }
//    }
//    public abstract boolean addDebitMoreThanSpendDebit(@Param("userId") String userId);
//
//    @Query(value = "SELECT NOT EXISTS (SELECT 1 FROM transactions WHERE user_id = :userId AND type = 'CREDIT')")
//    public abstract boolean noOneProductCredit(@Param("userId") String userId);
//
//    public abstract long sumSpendDebitMoreOneHundredThousandsRub(@Param("userId") String userId);
//}
