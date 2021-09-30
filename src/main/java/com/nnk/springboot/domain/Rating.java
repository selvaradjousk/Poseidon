package com.nnk.springboot.domain;

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
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating {

	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

	@Column(name = "moodys_rating",
			length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String moodysRating;

    @Column(name = "sand_prating",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String sandPRating;

    @Column(name = "fitch_rating",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

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

}
