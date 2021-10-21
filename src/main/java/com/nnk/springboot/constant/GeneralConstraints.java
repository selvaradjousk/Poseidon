package com.nnk.springboot.constant;


/**
 * The Class GeneralConstraints.
 */
public class GeneralConstraints {

    /**
     * Instantiates a new general constraints.
     */
    public GeneralConstraints() {
    }


	/** The Constant VARIABLE_LENGTH_125. */
	public static final int VARIABLE_LENGTH_125 = 125;

	/** The Constant VARIABLE_LENGTH_10. */
	public static final int VARIABLE_LENGTH_10 = 10;

	/** The Constant VARIABLE_LENGTH_30. */
	public static final int VARIABLE_LENGTH_30 = 30;

	/** The Constant VARIABLE_LENGTH_512. */
	public static final int VARIABLE_LENGTH_512 = 512;

	/** The Constant PATTERN_ALPHANUMERIC. */
	public static final String PATTERN_ALPHANUMERIC = "[-A-Za-z0-9\\s]+${2}";

	/** The Constant PATTERN_ALPHABETCHARACTERS. */
	public static final String PATTERN_ALPHABETCHARACTERS = "[-A-Za-z]+${2}";
	
	/** The Constant PATTERN_USERNAME. */
	public static final String PATTERN_USERNAME = "^[a-zA-Z][a-zA-Z0-9.@]+${2}";
}
