package com.nnk.springboot.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class CurvePointDTO.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurvePointDTO {

    /** The id. */
    private Integer id;

    /** The curve id. */
    @NotNull(message = "CurveId is mandatory")
    @Min(value = 0, message ="The value must be positive")
    private Integer curveId;

    /** The term. */
    @NotNull(message = "Term is mandatory")
    @Digits(integer = 10, fraction = 2,
	message="Invalid number input value : Maximum digits"
			+ " allowed are 10 and with 2 decimals fractions")
    @Min(value = 0, message ="The value must be positive")
    private Double term;

    /** The value. */
    @NotNull(message = "Value is mandatory")
    @Digits(integer = 10, fraction = 2,
	message="Invalid number input value : Maximum digits"
			+ " allowed are 10 and with 2 decimals fractions")
    @Min(value = 0, message ="The value must be positive")
    private Double value;

	// ############################################################

    /**
	 * Instantiates a new curve point DTO.
	 *
	 * @param curveId the curve id
	 * @param term the term
	 * @param value the value
	 */
	public CurvePointDTO(
    		final Integer curveId,
    		final Double term,
    		final Double value) {

    	this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

	// ############################################################

}
