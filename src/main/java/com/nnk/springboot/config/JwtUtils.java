package com.nnk.springboot.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtUtils {


	@Value("${poseidon.app.jwtSecret}")
	private String jwtSecret;

	@Value("${poseidon.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(
			Authentication authentication) {

		log.debug("### GENREATE JWT TOKEN called");
		log.debug("### Authentication input :"
				+ " {}", authentication );

		MyUserDetails userPrincipal
				= (MyUserDetails) authentication.getPrincipal();

		log.debug("### (MyUserDetails) authentication"
				+ ".getPrincipal() : {}", userPrincipal);

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date())
						.getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {

		log.debug("###  getUserNameFromJwtToken(String token)"
				+ " called TOKEN: {}", token);

		return Jwts.parser().setSigningKey(jwtSecret)
				.parseClaimsJws(token).getBody().getSubject();

	}

	public boolean validateJwtToken(String authToken) {

		log.debug("### VALIDATE JWT TOKEN: {}", authToken);

		try {

			Jwts.parser().setSigningKey(jwtSecret)
			.parseClaimsJws(authToken);

			return true;

		} catch (SignatureException e) {

			log.error("### Invalid JWT signature: {}", e.getMessage());

		} catch (MalformedJwtException e) {

			log.error("### Invalid JWT token: {}", e.getMessage());

		} catch (ExpiredJwtException e) {

			log.error("### JWT token is expired: {}", e.getMessage());

		} catch (UnsupportedJwtException e) {

			log.error("### JWT token is unsupported: {}", e.getMessage());

		} catch (IllegalArgumentException e) {

			log.error("### JWT claims string is empty: {}", e.getMessage());

		}

		return false;
	}
}