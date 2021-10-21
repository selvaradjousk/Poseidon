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
 * The Class BidList.
 */
@Builder
@Entity
@Table(name = "bidlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidList {

	
	/** The bid list id. */
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Integer bidListId;

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

    /** The bid quantity. */
    @Column(name = "bid_quantity",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double bidQuantity;

    /** The ask quantity. */
    @Column(name = "ask_quantity",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double askQuantity;

    /** The bid. */
    @Column(name = "bid",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double bid;

    /** The ask. */
    @Column(name = "ask",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double ask;

    /** The benchmark. */
    @Column(name = "benchmark",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String benchmark;

//    @CreationTimestamp
/** The bid list date. */
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    /** The commentary. */
    private String commentary;

    /** The security. */
    private String security;

    /** The status. */
    private String status;

    /** The trader. */
    private String trader;

    /** The book. */
    private String book;

    /** The creation name. */
    @Column(name = "creation_name")
    private String creationName;

    /** The creation date. */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /** The revision name. */
    @Column(name = "revision_name")
    private String revisionName;

    /** The revision date. */
    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    /** The deal name. */
    @Column(name = "deal_name")
    private String dealName;

    /** The deal type. */
    @Column(name = "deal_type")
    private String dealType;

    /** The source list id. */
    @Column(name = "source_list_id")
    private String sourceListId;

    /** The side. */
    private String side;

	// ############################################################

    /**
	 * Instantiates a new bid list.
	 *
	 * @param account the account
	 * @param type the type
	 * @param bidQuantity the bid quantity
	 */
	public BidList(
    		final String account,
    		final String type,
    		final Double bidQuantity) {

    	this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

	// ############################################################
   
}
