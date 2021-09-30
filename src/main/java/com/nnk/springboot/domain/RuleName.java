package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rulename")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleName {

	private static final int VARIABLE_LENGTH_125 = 125;

	private static final int VARIABLE_LENGTH_512 = 512;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", length = VARIABLE_LENGTH_125)
    private String name;

    @Column(name = "description", length = VARIABLE_LENGTH_125)
    private String description;

    @Column(name = "json", length = VARIABLE_LENGTH_125)
    private String json;

    @Column(name = "template", length = VARIABLE_LENGTH_512)
    private String template;

    @Column(name = "sql_str", length = VARIABLE_LENGTH_125)
    private String sqlStr;

    @Column(name = "sql_part", length = VARIABLE_LENGTH_125)
    private String sqlPart;

    
    public RuleName(final String name, final String description, final String json, final String template, final String sqlStr, final String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
    
}
