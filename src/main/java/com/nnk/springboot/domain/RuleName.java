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
 * The Class RuleName.
 */
@Builder
@Entity
@Table(name = "rulename")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RuleName {


	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /** The name. */
    @Column(name = "name",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String name;

    /** The description. */
    @Column(name = "description",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String description;

    /** The json. */
    @Column(name = "json",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String json;

    /** The template. */
    @Column(name = "template",
    		length = GeneralConstraints.VARIABLE_LENGTH_512)
    private String template;

    /** The sql str. */
    @Column(name = "sql_str",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String sqlStr;

    /** The sql part. */
    @Column(name = "sql_part",
    		length = GeneralConstraints.VARIABLE_LENGTH_125)
    private String sqlPart;

	// ############################################################

    /**
	 * Instantiates a new rule name.
	 *
	 * @param name the name
	 * @param description the description
	 * @param json the json
	 * @param template the template
	 * @param sqlStr the sql str
	 * @param sqlPart the sql part
	 */
	public RuleName(
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
