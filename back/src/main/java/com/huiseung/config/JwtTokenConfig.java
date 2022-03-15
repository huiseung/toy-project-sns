package com.huiseung.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenConfig {
    private String header;
    private String issuer;
    private String clientSecret;
    private int expirySeconds;
    //
    public String getHeader(){
        return header;
    }
    public String getIssuer(){
        return issuer;
    }
    public String getClientSecret(){
        return clientSecret;
    }
    public int getExpirySeconds(){
        return expirySeconds;
    }
    //
    public void setHeader(String header){
        this.header = header;
    }
    public void setIssuer(String issuer){
        this.issuer = issuer;
    }
    public void setClientSecret(String clientSecret){
        this.clientSecret = clientSecret;
    }
    public void setExpirySeconds(int expirySeconds){
        this.expirySeconds = expirySeconds;
    }
    //
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("header", header)
                .append("issuer", issuer)
                .append("clientSecret", clientSecret)
                .append("expirySeconds", expirySeconds)
                .toString();
    }
}
