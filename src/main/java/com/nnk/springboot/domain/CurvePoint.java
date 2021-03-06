package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurvePoint {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /** The curve id. */
    @Column(name = "curve_id")
    private Integer curveId;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    /** The as of date. */
    @Column(name = "as_of_date")
    private LocalDateTime asOfDate;

    /** The term. */
    @Column(name = "term",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double term;

    /** The value. */
    @Column(name = "value",
    		columnDefinition="Decimal(10,2) default '0.00'")
    private Double value;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    /** The creation date. */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

	// ############################################################

    /**
	 * Instantiates a new curve point.
	 *
	 * @param curveId the curve id
	 * @param term the term
	 * @param value the value
	 */
	public CurvePoint(
    		final Integer curveId,
    		final Double term,
    		final Double value) {

    	this.curveId = curveId;
        this.term = term;
        this.value = value;
    }	

	// ############################################################
	
}
