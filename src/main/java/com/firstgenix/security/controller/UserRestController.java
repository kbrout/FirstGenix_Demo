package com.firstgenix.security.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.firstgenix.model.security.Authority;
import com.firstgenix.model.security.Permission;
import com.firstgenix.model.security.User;
import com.firstgenix.security.JwtAuthenticationRequest;
import com.firstgenix.security.JwtTokenUtil;
import com.firstgenix.security.JwtUser;
import com.firstgenix.security.repository.AuthorityRepository;
import com.firstgenix.security.repository.PermisssionRepository;
import com.firstgenix.security.repository.UserRepository;
import com.firstgenix.security.service.JwtUserDetailsService;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PermisssionRepository permissionRepository;
    
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request){

   // System.out.println("test"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      JwtUser jwtuser=(JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      System.out.println(jwtuser.getFirstname());
      String token = request.getHeader(tokenHeader).substring(7);
      String username = jwtTokenUtil.getUsernameFromToken(token);
      JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
      System.out.println("Authorities "+user.getAuthorities());
      System.out.println("toString>>"+user.toString());
      
   // Permission permission=permissionRepository.findByName("ADMIN_PANEL");
   //  System.out.println("permission "+permission.getName()+" "+permission.getId());
 //     Authority authority=authorityRepository.findByName("ROLE_USER");
     // System.out.println("permission "+authority.toString());
 //     List<Permission> list = authority.getPermission();
  //    for (int i = 0; i < list.size(); i++){
  ///		System.out.println(list.get(i));
  //	}
      return user;
    }
    
    
@RequestMapping(value = "${jwt.route.registration.path}", method = RequestMethod.POST)
	
	public Response userreg(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, RegExceptionHandler{

      //  authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
          System.out.println("inside user rest controller "+authenticationRequest.getFirstname()+" "+authenticationRequest.getLastname()+" "+authenticationRequest.getPassword());
          User user=new User();
          System.out.println("uuid" +user.getId());
          user.setFirstname(authenticationRequest.getFirstname());
          user.setMiddlename(authenticationRequest.getMiddlename());
          user.setLastname(authenticationRequest.getLastname());
          user.setUsername(authenticationRequest.getUsername());
          user.setPassword(authenticationRequest.getPassword());
     //cc     user.setEmail(authenticationRequest.getEmail());
    	//  try {
		   ((JwtUserDetailsService)userDetailsService).saveUser(user);
			return Response.ok().entity("Registration success !!").build();
	
	   
    }
    


@RequestMapping(value = "${jwt.route.userupdate.path}", method = RequestMethod.PUT)

public Response EditUser(@PathVariable("id")String id, @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, ExceptionHandler_update{
      User user=new User();
      user.setFirstname(authenticationRequest.getFirstname());
      user.setLastname(authenticationRequest.getLastname());
      user.setUsername(authenticationRequest.getUsername());
      user.setPassword(authenticationRequest.getPassword());
    //  user.setEmail(authenticationRequest.getEmail());

	
	   ((JwtUserDetailsService)userDetailsService).editUser(user,id);
		return Response.ok().entity("Update success !!").build();
}

@RequestMapping(value = "${jwt.route.userdelete.path}", method = RequestMethod.DELETE)
public Response DeleteUser(@PathVariable("id")String id) throws AuthenticationException, ExceptionHandler_delete{
	   ((JwtUserDetailsService)userDetailsService).deleteUser(id);
		return Response.ok().entity("Delete success !!").build();
}


@RequestMapping(value = "${jwt.route.getuser.path}", method = RequestMethod.POST)
public JwtUser getUser(@PathVariable("id")String id) {
	 JwtUser user=   (JwtUser)((JwtUserDetailsService)userDetailsService).getUser(id);
		return user;
}

@RequestMapping(value = "${jwt.route.checkAuthority.path}",method = RequestMethod.GET)
public boolean checkAuthority(HttpServletRequest request,@PathVariable("aid")String id){
	 String token = request.getHeader(tokenHeader).substring(7);
     String username = jwtTokenUtil.getUsernameFromToken(token);
     JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
     String authorityname=((JwtUserDetailsService)userDetailsService).findAuthorityName(id);
	 if(((JwtUserDetailsService) userDetailsService).userHasAuthority(authorityname,user)){
		 return true;
	 }else{
		 return false; 
	 }
}

}

    
    


