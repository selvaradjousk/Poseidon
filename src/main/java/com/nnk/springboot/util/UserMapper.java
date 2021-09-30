package com.nnk.springboot.util;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;

public class UserMapper {

    public UserDTO toUserDTO(final User user) {

        return new UserDTO(
        		user.getId(),
        		user.getUsername(),
        		user.getPassword(),
        		user.getFullname(),
        		user.getRole());
    }

    public User toUser(final UserDTO userDTO) {

        return new User(
        		userDTO.getUsername(),
        		userDTO.getPassword(),
        		userDTO.getFullname(),
        		userDTO.getRole());
    }
}
