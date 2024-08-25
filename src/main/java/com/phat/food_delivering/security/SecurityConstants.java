package com.phat.food_delivering.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "zzKuADl6R1vgbGYESb7meWuGvONSRgJ0Ps3rJZkP1HiYCpnsqu3BcZY2usMPxI8A";
    public static final String HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    public static final String AUTH_PATH = "/auth/signup";
}
