package com.fullstack.pmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static  final String H2_URL="h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer "; // very important there should be a space after Bearer
    public static final String HEADER_STRING="Authorization";
    public static final long  EXIPIRATION_TIME= 3000_000;//30 secs
}
