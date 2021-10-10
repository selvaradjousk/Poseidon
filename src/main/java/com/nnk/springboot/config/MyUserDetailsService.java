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
    @Override
    @Transactional
    public UserDetails loadUserByUsername(
    		final String username) throws UsernameNotFoundException {

    	User user = userRepository
    			.findByUsername(username);
    	
    	log.info("Username : " + user.getUsername());
    	try {
    		if (user == null) 
    				throw new UsernameNotFoundException (
    						"User not found");
    			} catch(UsernameNotFoundException e) {
    			    throw e;
    			  }
        return MyUserDetails.build(user);
    }


	// *******************************************************************

	//    // *******************************************************************
//	/**
//	 * Loads user detail by username.
//	 *
//	 * @param email the user email
//	 * @return the user details
//	 * @throws UsernameNotFoundException the username not found exception
//	 * @throws DataAccessException the data access exception
//	 */
//	@Override
//    @Transactional
//	public UserDetails loadUserByUsername(final String username)
//			throws UsernameNotFoundException {
//
//		// https://stackoverflow.com/questions/59352914
//		// /custom-spring-boot-login-form
//
//		log.debug("Fetching user - MyUserDetailsService.loadUserByUsername");
////
////		Optional<User> user = userRepository.findUserByUsername(username);
////		log.info("Transforming " + user + " into UserDetails object");
////		user.orElseThrow(() -> new UsernameNotFoundException(
////				"Username: " + username + " not found"));
////		  log.info("About to return " + user.map(MyUserDetails::new).get());
////		return user.map(MyUserDetails::new).get();
//
//		// short form of the above codes
//		return userRepository.findUserByUsername(username)
//			    .map(MyUserDetails::new)
//			    .orElseThrow(() -> new UsernameNotFoundException(
//			    		"Username: " + username + " not found"));
//	}
//    // *******************************************************************
//	// try for testing https://stackoverflow.com/questions/38330597
//	// /inject-authenticationprincipal-when-unit-testing
//	// -a-spring-rest-controller
//    // *******************************************************************



}