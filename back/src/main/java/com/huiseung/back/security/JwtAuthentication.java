package com.huiseung.back.security;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class JwtAuthentication {
    private final Long id;
    private final String name;
    private final String email;

    public JwtAuthentication(
            Long id,
            String name,
            String email
    ){
        checkArgument(id != null,"id must be provided");
        checkArgument(name != null, "name must be provided");
        checkArgument(email != null, "email must be provided");
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("email", email)
                .toString();
    }
}
