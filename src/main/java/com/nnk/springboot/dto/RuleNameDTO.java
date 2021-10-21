package com.nnk.springboot.dto;

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
 * The Class RuleNameDTO.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RuleNameDTO {

    /** The id. */
    private Integer id;

    /** The name. */
    @NotBlank(message = "Name is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
    	message = "The maximum length for name can be"
    			+ " 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more"
			+ " than 2 characters")
    private String name;

    /** The description. */
    @NotBlank(message = "Description is mandatory")
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for description can be"
			+ " 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more"
			+ " than 2 characters")
    private String description;

    /** The json. */
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
        	message = "The maximum length for json can"
        			+ " be 125 characters")
    private String json;

    /** The template. */
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_512,
        	message = "The maximum length for template can"
        			+ " be 512 characters")
    private String template;

    /** The sql str. */
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
        	message = "The maximum length for sqlStr can"
        			+ " be 125 characters")
    private String sqlStr;

    /** The sql part. */
    @Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
        	message = "The maximum length for sqlPart can"
        			+ " be 125 characters")
    private String sqlPart;


	// ############################################################

    /**
	 * Instantiates a new rule name DTO.
	 *
	 * @param name the name
	 * @param description the description
	 * @param json the json
	 * @param template the template
	 * @param sqlStr the sql str
	 * @param sqlPart the sql part
	 */
	public RuleNameDTO(
    		final String name,
    		final String description,
    		final String json,
    		final String template,
    		final String sqlStr,
    		final String sqlPart) {

    	this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

	// ############################################################

}
