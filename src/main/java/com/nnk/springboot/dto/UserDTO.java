package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.nnk.springboot.constant.GeneralConstraints;
import com.nnk.springboot.constant.UserConstraints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

	private Integer id;

	@NotBlank(message = "Username is mandatory")
	@Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
		message = "The maximum length for username should be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_USERNAME,
	message = "Should be alphanumeric or email and minimum more than 2 characters")
	private String username;

	@NotBlank(message = "Password is mandatory")
	@Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for password should be 125 characters")
	@Pattern(regexp = UserConstraints.PATTERN_PASSWORD,
	message = "The password must contain at least"
			+ " 8 characters that includes"
			+ " any one uppercase letter,"
			+ " any one number and"
			+ " any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )")
	private String password;

	@NotBlank(message = "FullName is mandatory")
	@Length(max = GeneralConstraints.VARIABLE_LENGTH_125,
	message = "The maximum length for FullName should be 125 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHANUMERIC,
	message = "Should be alphanumeric and minimum more than 2 characters")
	private String fullname;

	@NotBlank(message = "Role is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABETCHARACTERS,
	message = "Should be alphabets and minimum more than 2 characters")
	private String role;

	// ############################################################

    public UserDTO(
    		final String username,
    		final String password,
    		final String fullname,
    		final String role) {

    	this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

	// ############################################################

}
