package com.adservio.reservation.security;

public class SecurityParams {
    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String PRIVATE_SECRET = "adservio";
    public static final long JWT_EXPIRATION = 10 * 24 * 3600 * 1000;
    public static final String JWT_HEADER_PREFIX = "Bearer ";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
}
