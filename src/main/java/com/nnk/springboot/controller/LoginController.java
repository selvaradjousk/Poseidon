package com.nnk.springboot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
//@RequestMapping("app")
public class LoginController {

//    @Autowired
//    private UserRepository userRepository;

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    // ******************************************************************
    // what way article-details link to user list ???? Need clarity!!!!
    // ******************************************************************    
//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        return mav;
//    }

//  @GetMapping("login")
//  public String login() {

//      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      if ( authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//          return "login";
//      }
//      return "login";
//  }

  // https://www.bezkoder.com/spring-boot-jwt-authentication/
  //https://www.baeldung.com/spring-security-oauth-principal-authorities-extractor
  // https://stackoverflow.com/questions/57434960/how-do-i-get-user-details-after-user-successfuly-logs-in-with-github-i-want-to
  // https://spring.io/guides/tutorials/spring-boot-oauth2/
  // http://5.9.10.113/58200573/create-custom-oauth-login-page-and-custom-jwt-token
  // https://www.amitph.com/spring-security-oauth2-login/#Github_OAuth2_Login
  // ******************************************************************
  // what way article-details link to user list ???? Need clarity!!!!
  // ******************************************************************    
//  @GetMapping("secure/article-details")
//  public ModelAndView getAllUserArticles() {
//      ModelAndView mav = new ModelAndView();
//      mav.addObject("users", userRepository.findAll());
//      mav.setViewName("user/list");
//      return mav;
//  }

    @GetMapping("error")
    public ModelAndView error(@AuthenticationPrincipal OAuth2User principal) {
        ModelAndView mav = new ModelAndView();

        String errorMessage= "You are not authorized"
        		+ " for the requested data.";

        mav.addObject("errorMsg", errorMessage);

        if (principal != null) {

        	mav.addObject("login", principal
        			.getAttribute("login"));
        }

        mav.setViewName("403");

        log.error("Login redirect 403 error");

        return mav;
    }
}
