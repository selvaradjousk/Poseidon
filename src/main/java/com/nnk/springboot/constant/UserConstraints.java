package com.nnk.springboot.constant;

/**
 * The Class UserConstraints.
 */
public class UserConstraints {

    /**
     * Instantiates a new user constraints.
     */
    public UserConstraints() {
    }

    /** The Constant PATTERN_PASSWORD. */
    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&~#@=*-+!€^$£µ%])(?=\\S+$).{8,}$";
}
