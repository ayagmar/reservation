package com.adservio.reservation.security;

import com.adservio.reservation.entities.CustomUserdetails;
import com.adservio.reservation.entities.User;
import com.adservio.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserDetailsSrvc implements UserDetailsService {

    private final UserService accountService;

    public UserDetailsSrvc(UserService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User appUser = accountService.loadUserByUsername(s);
        if (appUser == null) throw new UsernameNotFoundException("Invalid User");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(appRole -> {
            authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
        });
        CustomUserdetails customUserdetails=new CustomUserdetails();
        customUserdetails.setUser(appUser);
        customUserdetails.setAuthorities(authorities);
        return customUserdetails;
    }
}
