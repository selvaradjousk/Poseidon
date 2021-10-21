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


/**
 * The Class Trade.
 */
@Builder
@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trade {

	/** The trade id. */
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "trade_id", unique = true, nullable = false)
    private Integer tradeId;

    /** The account. */
    @Column(name = "account",
    		length = GeneralConstraints.VARIABLE_LENGTH_30,
    		nullable = false)
    private String account;

    /** The type. */
    @Column(name = "type",
    		length = GeneralConstraints.VARIABLE_LENGTH_30,
    		nullable = false)
    private String type;

    /** The buy quantity. */
    @Column(name = "buy_quantity",
    		columnDefinition ="Decimal(10,2) default '0.00'")
    private Double buyQuantity;

    /** The sell quantity. */
    @Column(name = "sell_quantity",
    		columnDefinition ="Decimal(10,2) default '0.00'")
    private Double sellQuantity;

    /** The buy price. */
    @Column(name = "buy_price",
    		columnDefinition ="Decimal(10,2) default '0.00'")
    private Double buyPrice;

    /** The sell price. */
    @Column(name = "sell_price",
    		columnDefinition ="Decimal(10,2) default '0.00'")
    private Double sellPrice;

//    @Column(name = "trade_date", nullable = false, updatable = false)
//    @CreationTimestamp
/** The trade date. */
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trade_date")
    private LocalDateTime tradeDate;

    /** The security. */
    @Column(name = "security",
    		length = GeneralConstraints.VARIABLE_LENGTH_125,
    		nullable = true)
    private String security;

    /** The status. */
    @Column(name = "status",
    		length = GeneralConstraints.VARIABLE_LENGTH_10)
    private String status;

    /** The trader. */
    @Column(name = "trader",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String trader;

    /** The benchmark. */
    @Column(name = "benchmark",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String benchmark;

    /** The book. */
    @Column(name = "book",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String book;

    /** The creation name. */
    @Column(name = "creation_name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String creationName;

//    @CreationTimestamp
/** The creation date. */
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /** The revision name. */
    @Column(name = "revision_name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String revisionName;

//    @UpdateTimestamp
/** The revision date. */
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    /** The deal name. */
    @Column(name = "deal_name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String dealName;

    /** The deal type. */
    @Column(name = "deal_type",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String dealType;

    /** The source list id. */
    @Column(name = "source_list_id",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String sourceListId;

    /** The side. */
    @Column(name = "side",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String side;

	// ############################################################

    /**
	 * Instantiates a new trade.
	 *
	 * @param account the account
	 * @param type the type
	 */
	public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

	// ############################################################

    /**
	 * Instantiates a new trade.
	 *
	 * @param account the account
	 * @param type the type
	 * @param buyQuantity the buy quantity
	 */
	public Trade(
    		final String account,
    		final String type,
    		final double buyQuantity) {

    	this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

	// ############################################################

}
