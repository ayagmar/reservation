package com.adservio.reservation.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsSrvc userDetailsSrvc;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserDetailsSrvc userDetailsSrvc, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsSrvc = userDetailsSrvc;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin();
        http.csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
//        http.authorizeRequests().antMatchers("/api/**/save/**", "/api/**/edit/**","/api/delete/**","/api/**/all","/api/Find**/**").hasAuthority("ADMIN");
//        http.authorizeRequests().anyRequest().authenticated();


    }
}

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsSrvc).passwordEncoder(bCryptPasswordEncoder);    }
//}

