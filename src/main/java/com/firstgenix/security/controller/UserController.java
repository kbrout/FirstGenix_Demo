package com.firstgenix.security.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstgenix.model.security.Authority;
import com.firstgenix.model.security.User;
import com.firstgenix.security.JwtAuthenticationRequest;
import com.firstgenix.security.repository.UserRepository;
import com.firstgenix.security.service.JwtUserDetailsService;
@RestController
public class UserController {
    @Autowired
	private UserRepository userRepository;
    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
    
    @RequestMapping(value = "${jwt.route.userrole.path}",method = RequestMethod.POST)
    public Response createRole(@RequestBody Authority authority) throws RoleExceptionHandler{

          System.out.println("inside user controller "+authority.getName());
       
  		   ((JwtUserDetailsService)userDetailsService).saveRole(authority);
        
      
  	return Response.ok().entity("Role Create success !!").build();
    }
  
	 
	 
	    
}
