package com.firstgenix.security.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.xml.ws.Response;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.firstgenix.model.security.Authority;
import com.firstgenix.model.security.User;
import com.firstgenix.security.JwtAuthenticationRequest;
import com.firstgenix.security.JwtUser;
import com.firstgenix.security.JwtUserFactory;
import com.firstgenix.security.controller.ExceptionHandler_delete;
import com.firstgenix.security.controller.ExceptionHandler_update;
import com.firstgenix.security.controller.RegExceptionHandler;
import com.firstgenix.security.controller.RoleExceptionHandler;
import com.firstgenix.security.repository.AuthorityRepository;
import com.firstgenix.security.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthorityRepository authorityRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
  
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
       
        //System.out.println("permission "+user.getPermission());
            return JwtUserFactory.create(user);
        }
    }
    
  public void saveUser(User authenticationRequest) throws RegExceptionHandler {
	 
	  User userExists =userRepository.findByUsername(authenticationRequest.getUsername());
  	     if (userExists != null) {
			 throw new RegExceptionHandler();
		  }else{
	  authenticationRequest.setPassword(bCryptPasswordEncoder.encode(authenticationRequest.getPassword()));
	  authenticationRequest.setEnabled(true);
	  authenticationRequest.setLastPasswordResetDate(new Date());
	  System.out.println("1@@@@@ "+authenticationRequest.getPassword()+" "+authenticationRequest.getFirstname()+" "+authenticationRequest.getLastname()+" "+authenticationRequest.getUsername()+" "+authenticationRequest.getId()+" "+authenticationRequest.getEnabled()+" "+authenticationRequest.getLastPasswordResetDate());
	 // User user= userRepository.save(authenticationRequest);
	  userRepository.save(authenticationRequest);
	  System.out.println("2@@@@@");
}
	//  return JwtUserFactory.create(userRepository.save(authenticationRequest));
	  
	  /*if (user == null) {
          throw new UsernameNotFoundException(String.format("No user found with username"));
      } else {
    	  System.out.println("3@@@@@");
          return JwtUserFactory.create(user);
      }*/
	  
      /*Role userRole = roleRepository.findByRole("ADMIN");
      user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));*/

	}
  public void editUser(User user,String id) throws ExceptionHandler_update  {
		 
	 // User userExists =userRepository.findByUsername(id);
	     Optional<User> userExists =userRepository.findById(id);
	           if (userExists.isPresent()){
	    	   User user_new = userExists.get();
	    	//   if (user_new != null) {
	    	   user_new.setFirstname(user.getFirstname());
	    	   user_new.setLastname(user.getLastname());
	    	   user_new.setUsername(user.getUsername());
	    	   user_new.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    	   user_new.setEnabled(true);
	    	   user_new.setLastPasswordResetDate(new Date());
	    	   userRepository.save(user_new); 
	    	}else{
	 		     throw new ExceptionHandler_update(); 
	   		}
  }
  
  
  
  public void deleteUser(String id) throws ExceptionHandler_delete  {
	  Optional<User> userExists =userRepository.findById(id);
	     if (userExists.isPresent()){
	    	   User user_new = userExists.get();
	    	//   if (user_new != null) {
	    	   user_new.setEnabled(false);
	    	   userRepository.save(user_new);
	     }else{
			  throw new ExceptionHandler_delete(); 
		}
	  
  } 
  
  
  public void saveRole(Authority authority) throws RoleExceptionHandler  {
		 
	  Authority roleExists =authorityRepository.findByName(authority.getName());
  	     if (roleExists != null) {
			 throw new RoleExceptionHandler();
		  }else{
	 // System.out.println("1@@@@@ "+authenticationRequest.getEmail()+" "+authenticationRequest.getPassword()+" "+authenticationRequest.getFirstname()+" "+authenticationRequest.getLastname()+" "+authenticationRequest.getUsername()+" "+authenticationRequest.getId()+" "+authenticationRequest.getEnabled()+" "+authenticationRequest.getLastPasswordResetDate());
	  authorityRepository.save(authority);
	  System.out.println("2@@@@@");
}

	}

public UserDetails getUser(String id) {
	 User user_new = null;
	 Optional<User> userExists =userRepository.findById(id);
     if (userExists.isPresent()){
	  user_new = userExists.get();	
     }
	 return JwtUserFactory.create(user_new);
	
		
}

public boolean userHasAuthority(String authority,JwtUser user){
	List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();

    for (GrantedAuthority grantedAuthority : authorities) {
        if (authority.equals(grantedAuthority.getAuthority())) {
            return true;
        }
    }
	return false;
}

public String findAuthorityName(String id) {
	 String name="";
	 Authority authority = null;
	 Optional<Authority> authExists =authorityRepository.findById(id);
     if (authExists.isPresent()){
    	 authority = authExists.get();	
    	 name=authority.getName(); 
     }
	return name;
}
  
  
  
    
}
