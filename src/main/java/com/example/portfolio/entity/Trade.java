package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userAccId;
    private String tradeType;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
