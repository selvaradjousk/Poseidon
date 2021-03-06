package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.exception.DataAlreadyExistsException;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.util.UserMapper;

import lombok.extern.log4j.Log4j2;

/**
 * The Class UserService.
 */
@Log4j2
@Service
public class UserService implements IUserService {

	/** The user repository. */
	@Autowired
	private final UserRepository userRepository;

	/** The user mapper. */
	@Autowired
	private final UserMapper userMapper;

	/** The password encoder. */
	private BCryptPasswordEncoder passwordEncoder;




	// *******************************************************************


	/**
	 * Instantiates a new user service.
	 *
	 * @param userRepository the user repository
	 * @param userMapper the user mapper
	 */
	public UserService(
			final UserRepository userRepository,
			final UserMapper userMapper) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}



	// *******************************************************************


	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	@Override
	public List<UserDTO> getAllUser() {

		List<User> users = userRepository.findAll();
		List<UserDTO> userList = new ArrayList<>();

		log.info("Request: UserService.userRepository.findAll()"
				+ " - ListSize: {} users", users.size());

        if (users.isEmpty()) {

        	log.error("ERROR on Request: UserService"
        			+ ".userRepository.findAll()"
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


	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */
	@Override
	public UserDTO getUserById(final int userId) {

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


	/**
	 * Adds the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user DTO
	 */
	@Override
	public UserDTO addUser(final UserDTO userDTO) {

		User userExistsCheck = userRepository
				.findByUsername(userDTO.getUsername());


		if (userExistsCheck != null) {

    	log.error("ERROR on Request: userRepository.findByUsername"
				+ " - User Exists: {} <= user",
				userExistsCheck.getUsername());

			throw new DataAlreadyExistsException(
            		"Username already exists");
        }

        log.info("Request: userRepository.findByUsername"
        		+ "User ID: {} & UserName: {} NOT FOUND ==> OK to ADD",
        		userDTO.getId(), userDTO.getUsername());

		User userToAdd = userMapper
				.toUser(userDTO);

		log.info("Request: UserToAdd => {}", userToAdd.getUsername());
		log.info("Request: UserPassword Before Encoding => {}",
				userToAdd.getPassword());

		passwordEncoder = new BCryptPasswordEncoder();

		userToAdd.setPassword(passwordEncoder
        		.encode(userDTO.getPassword()));

		log.info("Request: UserPassword After Encoding => {}",
				userToAdd.getPassword());

		User userAdded = userRepository
        		.save(userToAdd);

		log.info("Request: UserAdded Successfully => {}",
				userAdded.getUsername());

		return userMapper
        		.toUserDTO(userAdded);
        }



	// *******************************************************************


	/**
	 * Update user.
	 *
	 * @param userId the user id
	 * @param userDTO the user DTO
	 * @return the user DTO
	 */
	@Override
    public UserDTO updateUser(final int userId, final UserDTO userDTO) {

        userRepository.findById(userId).orElseThrow(() ->
                new DataNotFoundException("User Does not Exist"));


        log.info("Request: userRepository.findById(userId)"
        		+ "User ID: {} ",
        		userRepository.findById(userId));


        User userToUpdate = userMapper
        		.toUser(userDTO);

		log.info("Request: userToUpdate => {}",
				userToUpdate.getUsername());
		log.info("Request: UserPassword Before Encoding => {}",
				userToUpdate.getPassword());

        userToUpdate.setId(userId);

        passwordEncoder = new BCryptPasswordEncoder();

        userToUpdate.setPassword(passwordEncoder
        		.encode(userDTO.getPassword()));


		log.info("Request: UserPassword After Encoding => {}",
				userToUpdate.getPassword());

        User userUpdated = userRepository
        		.save(userToUpdate);

		log.info("Request: UserAdded Successfully => {}",
				userUpdated.getUsername());

        return userMapper
        		.toUserDTO(userUpdated);
    }



	// *******************************************************************


	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 */
	@Override
	   public void deleteUser(final int userId) {

		log.info("Request: UserToDelete ID => {}",
				userId);

		userRepository.findById(userId).orElseThrow(() ->
             new DataNotFoundException("User Not Found"));

		log.info("Request: UserToDelete ID => {} FOUND",
				userId);

		userRepository.deleteById(userId);

		log.info("Request: UserToDelete ID => {} DELETED",
				userId);

	}



	// *******************************************************************


}
