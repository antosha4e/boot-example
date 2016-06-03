package com.bootexample.auth;

import com.bootexample.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created: antosha4e
 * Date: 12.05.16
 */
public class BootAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private User user;

    public BootAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BootAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public BootAuthenticationToken(User user) {
        super(user.getName(), null, getUserAuths(user));

        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> getUserAuths(User user) {
        if(user.getModules() == null)
            return Collections.emptyList();
        return user.getModules().stream().map(m -> new SimpleGrantedAuthority("ROLE_" + m)).collect(Collectors.toList());
    }

    public User getUser() {
        return user;
    }
}