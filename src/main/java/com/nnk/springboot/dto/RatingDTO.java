package com.nnk.springboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.nnk.springboot.constant.GeneralConstraints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class RatingDTO.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingDTO {

    /** The id. */
    private Integer id;

    /** The moodys rating. */
    @NotBlank(message = "Moodys Rating is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
    	message = "The maximum length for moodysRating"
    			+ " can be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more"
			+ " than 2 characters")
    private String moodysRating;

    /** The sand P rating. */
    @NotBlank(message = "SandPRating is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for sandPRating can be"
			+ " 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more"
			+ " than 2 characters")
    private String sandPRating;

    /** The fitch rating. */
    @NotBlank(message = "FitchRating is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for fitchRating can be"
			+ " 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more"
			+ " than 2 characters")
    private String fitchRating;

    /** The order number. */
    @Min(value = 0, message ="The value must be positive")
    private Integer orderNumber;

	// ############################################################

    /**
	 * Instantiates a new rating DTO.
	 *
	 * @param moodysRating the moodys rating
	 * @param sandPRating the sand P rating
	 * @param fitchRating the fitch rating
	 * @param orderNumber the order number
	 */
	public RatingDTO(
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
