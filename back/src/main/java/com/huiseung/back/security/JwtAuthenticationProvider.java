package com.huiseung.back.security;

import com.huiseung.back.entity.user.User;
import com.huiseung.back.error.NotFoundException;
import com.huiseung.back.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final Jwt jwt;
    private final UserService userService;

    public JwtAuthenticationProvider(
            Jwt jwt,
            UserService userService
    ){
        this.jwt = jwt;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(authenticationToken.authenticationRequest());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication processUserAuthentication(
            AuthenticationRequest request
    ){
        try{
            User user = userService.login(request.getPrincipal(), request.getCredentials());
            JwtAuthenticationToken authenticated = new JwtAuthenticationToken(
                    new JwtAuthentication(user.getId(), user.getName(), user.getEmail()),
                    null,
                    createAuthorityList()
            );
            return authenticated;
        }catch (NotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
