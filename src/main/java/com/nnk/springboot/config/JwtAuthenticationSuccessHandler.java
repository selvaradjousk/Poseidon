package com.nnk.springboot.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.WebUtils;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthenticationSuccessHandler
				implements AuthenticationSuccessHandler {


	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;



  private final RedirectStrategy redirectStrategy
  						= new DefaultRedirectStrategy();



	// ##############################################################

  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication)
                                    		  throws IOException {

	  log.info("### response headerNames: "+ response.getHeaderNames());

	  log.info("### request Principle: "+ request.getUserPrincipal());

	  log.info("### request Cookies: "+ request.getCookies());
//	  log.info("### request JWT = WebUtils.getCookie(request, \"token\")"
//	  		+ ".getValue(): "
//			  + (WebUtils.getCookie(request, "token")).getValue());
	  log.info("### request WebUtils.getCookie(request, \"token\"):"
	  		+ " "+ WebUtils.getCookie(request, "token"));

	  log.info("### Autentication Principle:"
	  		+ " "+ authentication.getPrincipal());

	  log.info("### Autentication Details:"
	  		+ " "+ authentication.getDetails());

	  log.info("### Autentication Authorities:"
	  		+ " "+ authentication.getAuthorities());

	  log.info("### Autentication Name:"
	  		+ " "+ authentication.getName());

	  log.info("### Autentication Credentials:"
	  		+ " "+ authentication.getCredentials());



	  // ############################################################
	  // DETERMINE IF OAUTH USER IN DATABASE OR NOT IF NOT ADDED TO DB
	  // ############################################################
	  String userName = authentication.getName();

	  if ((userRepository.findUserByUsername(userName))
			  .isPresent() == false) {
		  
		    UserDTO createUserDto = new UserDTO();
		    createUserDto.setUsername(authentication.getName());

		    
		    if((authentication.getPrincipal().toString()
		    		.contains("github"))) {

		    	createUserDto.setFullname("Github");

		    } else if ((authentication.getPrincipal().toString().contains("google"))) {

		    	createUserDto.setFullname("Google");	

		    } else {

		    	createUserDto.setFullname("WhoAreYou");
		    }
		    
		    createUserDto.setRole("USER");

		    createUserDto.setPassword("Password1!");

		    userService.addUser(createUserDto);

		    User UserFoundAfterUpdate = userRepository
		    		.findByUsername(authentication.getName());

		    if (UserFoundAfterUpdate != null) {

		    log.info("### New oauth user added to database:"
		    		+ " "+ UserFoundAfterUpdate.getUsername()
		    		+ " SUCCESSFULLY");
		    }
	  }
	  // ############################################################





    handle(request, response, authentication);
    clearAuthenticationAttributes(request);


  }


	// ##############################################################

  protected void handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication)
                        		throws IOException {

//    String targetUrl = "/oauthregister";
    
    String targetUrl = "/login";
    
//    String targetUrl = determineTargetUrl(authentication);

    log.info("### HANDLE - targetUrl: "+ targetUrl);

    if (response.isCommitted()) {
      log.debug(
          "Response has already been committed. Unable to redirect to "
              + targetUrl);
      return;
    }

    log.info("### redirectStrategy.sendRedirect("
    		+ "request,"
    		+ " response,"
    		+ " targetUrl)");

    redirectStrategy.sendRedirect(
    		request,
    		response,
    		targetUrl);
  }

  // ****************************************************************

//  protected String determineTargetUrl(
//		  final Authentication authentication) {
//
//	    log.info("### determineTargetUrl(authentication)");
//
//    Map<String, String> roleTargetUrlMap = new HashMap<>();
//
//    roleTargetUrlMap.put("ROLE_USER", "/bidList/list");
////
////    roleTargetUrlMap.put("ROLE_ADMIN", "/user/list");
//
//    log.info("### roleTargetUrlMap updated by ROLE"
//    		+ " whether user or admin");
//
//    final Collection<? extends GrantedAuthority> authorities
//    				= authentication.getAuthorities();
//
//    log.info("### authorities: "+ authorities);
//
//    for (final GrantedAuthority grantedAuthority : authorities) {
//
//
//      String authorityName = grantedAuthority.getAuthority();
//
//      log.info("### authorityName: "+ authorityName);
//
//      if (roleTargetUrlMap.containsKey(authorityName)) {
//
//    	    log.info("### roleTargetUrlMap.containsKey(authorityName)"
//    	    		+ " " + authorityName);
//
//    	    log.info("### roleTargetUrlMap.get(authorityName) will"
//    	    		+ " be returned " + authorityName);
//    	  
//        return roleTargetUrlMap.get(authorityName);
//      }
//    }
//    throw new IllegalStateException();
//  }


	// ##############################################################

  protected void clearAuthenticationAttributes(
		  HttpServletRequest request) {

	  HttpSession session = request.getSession(false);

	  if (session == null) {

		  return;

	  }

	  session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

  }


	// ##############################################################

}