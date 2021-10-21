package com.nnk.springboot.domain;

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
 * The Class Rating.
 */
@Builder
@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating {


	/** The id. */
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

	/** The moodys rating. */
	@Column(name = "moodys_rating",
			length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String moodysRating;

    /** The sand P rating. */
    @Column(name = "sand_prating",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String sandPRating;

    /** The fitch rating. */
    @Column(name = "fitch_rating",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String fitchRating;

    /** The order number. */
    @Column(name = "order_number")
    private Integer orderNumber;

	// ############################################################

    /**
	 * Instantiates a new rating.
	 *
	 * @param moodysRating the moodys rating
	 * @param sandPRating the sand P rating
	 * @param fitchRating the fitch rating
	 * @param orderNumber the order number
	 */
	public Rating(
    		final String moodysRating,
    		final String sandPRating,
    		final String fitchRating,
    		final Integer orderNumber) {

    	this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

	// ############################################################

}
