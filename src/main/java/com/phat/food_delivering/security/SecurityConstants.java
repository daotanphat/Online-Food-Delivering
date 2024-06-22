package com.phat.food_delivering.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)";
    public static final String HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    public static final String AUTH_PATH = "/auth/signup";
}
