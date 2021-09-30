package com.nnk.springboot.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
public class CurvePointDTO {

    private Integer id;

    @NotNull(message = "CurveId is mandatory")
    @Min(value = 0 , message ="The value must be positive")
    private Integer curveId;

    @NotNull(message = "Term is mandatory")
    @Digits( integer = 10, fraction = 2,
	message="Invalid number input value : Maximum digits"
			+ " allowed are 10 and with 2 decimals fractions")
    @Min(value = 0 , message ="The value must be positive")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Digits( integer = 10, fraction = 2,
	message="Invalid number input value : Maximum digits"
			+ " allowed are 10 and with 2 decimals fractions")
    @Min(value = 0 , message ="The value must be positive")
    private Double value;


    public CurvePointDTO(
    		final Integer curveId,
    		final Double term,
    		final Double value) {

    	this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

}
