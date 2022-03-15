package com.huiseung.config;


import com.huiseung.back.security.EntryPointUnauthorizedHandler;
import com.huiseung.back.security.Jwt;
import com.huiseung.back.security.JwtAccessDeniedHandler;
import com.huiseung.back.security.JwtAuthenticationProvider;
import com.huiseung.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Jwt jwt;
    private final JwtTokenConfig jwtTokenConfig;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    public WebSecurityConfig(
            Jwt jwt,
            JwtTokenConfig jwtTokenConfig,
            JwtAccessDeniedHandler accessDeniedHandler,
            EntryPointUnauthorizedHandler unauthorizedHandler
    ){
        this.jwt = jwt;
        this.jwtTokenConfig = jwtTokenConfig;
        this.accessDeniedHandler = accessDeniedHandler;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(
            Jwt jwt,
            UserService userService
    ){
        return new JwtAuthenticationProvider(jwt, userService);
    }
    @Autowired
    public void configureAuthentication(
            AuthenticationManagerBuilder builder,
            JwtAuthenticationProvider authenticationProvider) {
        builder.authenticationProvider(authenticationProvider);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/swagger-resources", "/webjars/**", "/static/**", "/templates/**", "/v2/**", "/h2/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf()
                .disable();
        http.headers()
                .disable();
        http.formLogin()
                .disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/api/user/join").permitAll();

    }



}
