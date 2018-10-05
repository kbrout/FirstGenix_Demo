package com.firstgenix.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.firstgenix.model.security.Authority;
import com.firstgenix.model.security.Permission;
import com.firstgenix.model.security.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getMiddlename(),
                user.getLastname(),
              //user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
              //getAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }
     
            private static Collection<? extends GrantedAuthority>getAuthorities(
    	    Collection<Authority> roles) {
    	  
    	        return getGrantedAuthorities(getPrivileges(roles));
    	    }
    
    
    private static List<String> getPrivileges(Collection<Authority> roles) {    	  
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Authority role : roles){
            collection.addAll(role.getPermission());
        }
        for (Permission item : collection) {
        	permissions.add(item.getName());
        }
        return permissions;
    }
    
    
    

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities){
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
    
        private static List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
