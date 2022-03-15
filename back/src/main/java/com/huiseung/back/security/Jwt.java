package com.huiseung.back.security;

public class Jwt {
    private final String issuer;
    private final String clientSecret;
    private final int expirySeconds;

    public Jwt(
            String issuer,
            String clientSecret,
            int expirySeconds
    ){
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.expirySeconds = expirySeconds;

    }
    //
    public String getIssuer() {
        return issuer;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public int getExpirySeconds() {
        return expirySeconds;
    }
}
