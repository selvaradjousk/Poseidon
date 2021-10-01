package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nnk.springboot.constant.GeneralConstraints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trade {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "trade_id", unique = true, nullable = false)
    private Integer tradeId;

    @Column(name = "account",
    		length = GeneralConstraints.VARIABLE_LENGTH_30,
    		nullable = false)
    private String account;

    @Column(name = "type",
    		length = GeneralConstraints.VARIABLE_LENGTH_30,
    		nullable = false)
    private String type;

    @Column(name = "buy_quantity",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double buyQuantity;

    @Column(name = "sell_quantity",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double sellQuantity;

    @Column(name = "buy_price",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double buyPrice;

    @Column(name = "sell_price",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double sellPrice;

//    @Column(name = "trade_date", nullable = false, updatable = false)
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trade_date")
    private LocalDateTime tradeDate;

    @Column(name = "security",
    		length = GeneralConstraints.VARIABLE_LENGTH_125,
    		nullable = true)
    private String security;

    @Column(name = "status",
    		length = GeneralConstraints.VARIABLE_LENGTH_10)
    private String status;

    @Column(name = "trader",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String trader;

    @Column(name = "benchmark",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String benchmark;

    @Column(name = "book",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String book;

    @Column(name = "creation_name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String creationName;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "revision_name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String revisionName;

//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    @Column(name = "deal_name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String dealName;

    @Column(name = "deal_type",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String dealType;

    @Column(name = "source_list_id",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String sourceListId;

    @Column(name = "side",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Trade(
    		final String account,
    		final String type,
    		final double buyQuantity) {

    	this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
    
}
