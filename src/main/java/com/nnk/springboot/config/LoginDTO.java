package com.nnk.springboot.config;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

	// ##############################################################

    @NotEmpty(message = "Username is mandatory")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    private String password;

	// ##############################################################

}
