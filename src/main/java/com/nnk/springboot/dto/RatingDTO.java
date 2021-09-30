package com.nnk.springboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.nnk.springboot.constant.GeneralConstraints;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingDTO {

    private Integer id;

    @NotBlank(message = "Moodys Rating is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
    	message = "The maximum length for moodysRating can be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more than 2 characters")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for sandPRating can be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more than 2 characters")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for fitchRating can be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more than 2 characters")
    private String fitchRating;

    @Min(value = 0 , message ="The value must be positive")
    private Integer orderNumber;


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
}
