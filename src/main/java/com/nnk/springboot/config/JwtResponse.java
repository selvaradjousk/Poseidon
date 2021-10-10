package com.nnk.springboot.config;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {

	private String token;

	final private String type = "Bearer";

	private String username;

	private String password;

	private List<String> roles;

}