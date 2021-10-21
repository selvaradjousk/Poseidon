package com.nnk.springboot.config;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class LoginRequest.
 */
@Getter
@Setter
public class LoginRequest {

	/** The username. */
	@NotBlank
	private String username;

	/** The password. */
	@NotBlank
	private String password;

}