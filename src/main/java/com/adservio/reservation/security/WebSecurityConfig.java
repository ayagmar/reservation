package com.adservio.reservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//            JWTAuthenticationFilter jwtAuthenticationFilter=new JWTAuthenticationFilter(authenticationManager());
//            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable().
                authorizeRequests().anyRequest().permitAll();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//       http.authorizeRequests().antMatchers("/api/login/**", "/api/register/**").permitAll();
//      http.authorizeRequests().antMatchers("/api/**/save/**", "/api/**/edit/**","/api/delete/**","/api/**/all","/api/Find**/**").hasAuthority("ADMIN");
//      http.authorizeRequests().anyRequest().authenticated();
//      http.addFilter(jwtAuthenticationFilter);
//      http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);



    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}

