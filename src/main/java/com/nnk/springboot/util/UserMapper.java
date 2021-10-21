package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;


/**
 * The Class UserMapper.
 */
@Component
public class UserMapper {

    /**
     * To user DTO.
     *
     * @param user the user
     * @return the user DTO
     */
    public UserDTO toUserDTO(final User user) {

        return new UserDTO(
        		user.getId(),
        		user.getUsername(),
        		user.getPassword(),
        		user.getFullname(),
        		user.getRole());
    }

    /**
     * To user.
     *
     * @param userDTO the user DTO
     * @return the user
     */
    public User toUser(final UserDTO userDTO) {

        return new User(
        		userDTO.getUsername(),
        		userDTO.getPassword(),
        		userDTO.getFullname(),
        		userDTO.getRole());
    }
}
