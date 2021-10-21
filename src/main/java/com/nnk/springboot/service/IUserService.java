package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.UserDTO;


/**
 * The Interface IUserService.
 */
public interface IUserService {

	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	List<UserDTO> getAllUser();

	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */
	UserDTO getUserById(int userId);

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the user DTO
	 */
	UserDTO addUser(UserDTO user);

	/**
	 * Update user.
	 *
	 * @param userId the user id
	 * @param user the user
	 * @return the user DTO
	 */
	UserDTO updateUser(int userId, UserDTO user);

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 */
	void deleteUser(int userId);

}
