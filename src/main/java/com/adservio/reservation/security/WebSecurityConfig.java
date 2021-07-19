package com.adservio.reservation.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final UserDetailsService userDetailsService;
//
//    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
       //    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
     //      http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
       //    http.authorizeRequests().antMatchers("/api/delete**/**", "/api/update**/**","api/save**/**").hasAuthority("ADMIN");

    }

}

