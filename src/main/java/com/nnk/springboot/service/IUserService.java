package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.UserDTO;

public interface IUserService {

	List<UserDTO> getAllUser();

	UserDTO getUserById(final int userId);

}
