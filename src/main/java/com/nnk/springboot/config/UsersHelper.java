package com.nnk.springboot.config;

import org.springframework.security.core.userdetails.User;

public class UsersHelper extends User{

	private static final long serialVersionUID = 1L;
	   public UsersHelper(MyUserDetailsDTO myUserDetails) {
	      super(
	    		  myUserDetails.getUsername(),
	    		  myUserDetails.getPassword(),
	    		  myUserDetails.getListOfgrantedAuthorities()
	    		);
	   }
}