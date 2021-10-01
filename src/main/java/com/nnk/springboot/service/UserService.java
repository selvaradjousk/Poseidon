package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.util.UserMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService implements IUserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final BCryptPasswordEncoder passwordEncoder;




	// *******************************************************************	


	public UserService(
			final UserRepository userRepository,
			final UserMapper userMapper,
			final BCryptPasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}



	// *******************************************************************	


	@Override
	public List<UserDTO> getAllUser() {

		List<User> users = userRepository.findAll();
		List<UserDTO> userList = new ArrayList<>();

		log.info("Request: UserService.userRepository.findAll()"
				+ " - ListSize: {} users", users.size());		

        if (users.isEmpty()) {

        	log.error("ERROR on Request: UserService.userRepository.findAll()"
    				+ " - ListSize EMPTY: {} users", users.size());	
        	throw new DataNotFoundException("User List Not Available");
        }

        for (User user : users) {
			UserDTO userDTO = userMapper.toUserDTO(user);
	        userList.add(userDTO);
		}

        log.info("Request: userList.add(userDTO)"
        		+ " after userMapper.toUserDTO(user)"
				+ " - ListSize: {} users", userList.size());	

        return userList;
	    }


	// *******************************************************************	


	@Override
	public UserDTO getUserById(int userId) {

        User user = userRepository
        		.findById(userId).orElseThrow(() ->
                new DataNotFoundException("User Not Found"));


        log.info("Request: userRepository.findById(userId)"
        		+ "User ID: {} & UserName: {} ",
        		user.getId(), user.getUsername());

        return userMapper
        		.toUserDTO(user);
    }


	// *******************************************************************	


	@Override
	public UserDTO addUser(UserDTO userDTO) {

		userRepository.findByUsername(userDTO.getUsername());


	        User userToAdd = userMapper
	        		.toUser(userDTO);

	        userToAdd.setPassword(passwordEncoder
	        		.encode(userDTO.getPassword()));

	        User userAdded = userRepository
	        		.save(userToAdd);

	        return userMapper
	        		.toUserDTO(userAdded);
	    }

	// *******************************************************************	



}
