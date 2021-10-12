package com.nnk.springboot.constant;

public class GeneralConstraints {

    public GeneralConstraints() {
    }


	public static final int VARIABLE_LENGTH_125 = 125;

	public static final int VARIABLE_LENGTH_10 = 10;

	public static final int VARIABLE_LENGTH_30 = 30;

	public static final int VARIABLE_LENGTH_512 = 512;

	public static final String PATTERN_ALPHANUMERIC = "[-A-Za-z0-9\\s]+${2}";

	public static final String PATTERN_ALPHABETCHARACTERS = "[-A-Za-z]+${2}";
	
	public static final String PATTERN_USERNAME = "^[a-zA-Z][a-zA-Z0-9.@]+${2}";
}
