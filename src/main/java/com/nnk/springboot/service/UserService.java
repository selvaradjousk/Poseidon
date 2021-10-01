package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.util.UserMapper;

public class UserService implements IUserService {

	private UserRepository userRepository;

	private UserMapper userMapper;



	// *******************************************************************	


	@Override
	public List<UserDTO> getAllUser() {
		List<User> users = userRepository.findAll();
		List<UserDTO> userList = new ArrayList<>();

        for (User user : users) {
			UserDTO userDTO = userMapper.toUserDTO(user);
	        userList.add(userDTO);
		}

	    return userList;
	    }


	// *******************************************************************	

}
