package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.UserDTO;

public interface IUserService {

	List<UserDTO> getAllUser();

	UserDTO getUserById(final int userId);

	UserDTO addUser(final UserDTO user);

	UserDTO updateUser(final int userId, final UserDTO user);

}
