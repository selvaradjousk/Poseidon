package com.nnk.springboot.constant;

public class JwtConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 24*60*60;

    public static final String SIGNING_KEY = "poseidonSecretKey";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

}
