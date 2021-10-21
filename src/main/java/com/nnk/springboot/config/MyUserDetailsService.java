package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

/**
 * The Class MyUserDetailService custom implementation of UserDetailsService
 * interface.
 * 
 * @author Senthil
 */
@Log4j2
@Service
public class MyUserDetailsService implements UserDetailsService {

	/** The user repository. */
	@Autowired
	private final UserRepository userRepository;

	/**
	 * Constructor of class MyUserDetailsService Instantiates a new
	 *  my user detail service.
	 *
	 * @param userRepositoryy the user repositoryy
	 */
	public MyUserDetailsService(final UserRepository userRepositoryy) {
		this.userRepository = userRepositoryy;
	}

	// *******************************************************************

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(
    		final String username) throws UsernameNotFoundException {

    	User user = userRepository
    			.findByUsername(username);
    	
    	log.info("Username : " + user.getUsername());
    	try {
    		if (user == null) 
    				throw new UsernameNotFoundException(
    						"User not found");
    		} catch (UsernameNotFoundException e) {
    			    throw e;
    		}
        return MyUserDetails.build(user);
    }
	// *******************************************************************



}