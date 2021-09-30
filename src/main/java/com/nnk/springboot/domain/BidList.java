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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidList {

	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Integer bidListId;

	@Column(name = "account",
			length = GeneralConstraints.VARIABLE_LENGTH_30,
			nullable = false)
    private String account;

    @Column(name = "type",
    		length = GeneralConstraints.VARIABLE_LENGTH_30,
    		nullable = false)
	private String type;

    @Column(name = "bid_quantity",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double bidQuantity;

    @Column(name = "ask_quantity",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double askQuantity;

    @Column(name = "bid",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double bid;

    @Column(name = "ask",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double ask;

    @Column(name = "benchmark",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String benchmark;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    private String commentary;

    private String security;

    private String status;

    private String trader;

    private String book;

    @Column(name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    @Column(name = "deal_name")
    private String dealName;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "source_list_id")
    private String sourceListId;

    private String side;

    public BidList(
    		final String account,
    		final String type,
    		final Double bidQuantity) {

    	this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
    
}
