package com.nnk.springboot.dto;

import javax.validation.constraints.Digits;
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
 * The Class BidListDTO.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidListDTO {


    /** The bid list id. */
    private Integer bidListId;

    /** The account. */
    @NotBlank(message = "Account is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_30,
	message = "The maximum length for account should"
			+ " be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum"
			+ " more than 2 characters")
    private String account;

    /** The type. */
    @NotBlank(message = "Type is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_30,
    	message = "The maximum length for type should"
    			+ " be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum"
			+ " more than 2 characters")
    private String type;

    /** The bid quantity. */
    @Digits(integer = 10, fraction = 2,
        	message="Invalid number input value : Maximum digits"
        			+ " allowed are 10 and with 2"
        			+ " decimals fractions")
    @Min(value = 0, message = "The bidQuantity"
    		+ " must be positive")
    private Double bidQuantity;

	// ############################################################

    /**
	 * Instantiates a new bid list DTO.
	 *
	 * @param account the account
	 * @param type the type
	 * @param bidQuantity the bid quantity
	 */
	public BidListDTO(
    		final String account,
    		final String type,
    		final Double bidQuantity) {

    	this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

	// ############################################################

}
