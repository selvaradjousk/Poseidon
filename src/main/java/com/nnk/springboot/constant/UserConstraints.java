package com.nnk.springboot.constant;

public class UserConstraints {

    public UserConstraints() {
    }

    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&~#@=*-+!€^$£µ%])(?=\\S+$).{8,}$";
}
