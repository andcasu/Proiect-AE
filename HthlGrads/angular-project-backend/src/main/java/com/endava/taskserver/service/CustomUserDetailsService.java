package com.endava.taskserver.service;


import com.endava.taskserver.model.User;
import com.endava.taskserver.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = this.userService.findByEmail(email);
        UserDetails userDetails = UserDTO.builder().password(user.getPassword())
                .username(user.getEmail())
                .authorities(user.getAuthorities()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList())).build();


        return userDetails;
    }
}
