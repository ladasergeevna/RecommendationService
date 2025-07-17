package org.skypro.recommendationService.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recommendations")
public class RecommendationsByRules {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_text")
    private String productText;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rule> rule;


      public RecommendationsByRules() {
    }


        public UUID getProductId () {
            return productId;
        }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName () {
            return productName;
        }

        public void setProductName (String productName){
            this.productName = productName;
        }

        public String getProductText () {
            return productText;
        }

        public void setProductText (String productText){
            this.productText = productText;
        }

        public List<Rule> getRule () {
            return rule;
        }

        public void setRule (List < Rule > rule) {
            this.rule = rule;
        }

        @Override
        public boolean equals (Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecommendationsByRules that = (RecommendationsByRules) o;
            return Objects.equals(productId, that.productId);
        }

        @Override
        public int hashCode () {
            return Objects.hashCode(productId);
        }

        @Override
        public String toString () {
            return "RecommendationsByRules{" +
                    "productId=" + productId +
                    ", productName='" + productName + '\'' +
                    ", productText='" + productText + '\'' +
                    ", rule=" + rule +
                    '}';
        }
    }
