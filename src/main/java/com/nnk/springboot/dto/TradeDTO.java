package com.nnk.springboot.dto;

import javax.validation.constraints.Digits;
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
public class TradeDTO {

    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_30,
    	message = "The maximum length for account should be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more than 2 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_30,
    	message = "The maximum length for type should be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more than 2 characters")
    private String type;

    @Digits( integer = 10, fraction = 2,
    	message="Invalid number input value : Maximum digits"
    			+ " allowed are 10 and with 2 decimals fractions")
    @Min(value = 0 , message ="The value must be positive")
    private Double buyQuantity;

    public TradeDTO(
    		final String account,
    		final String type,
    		final Double buyQuantity) {

    	this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

}
