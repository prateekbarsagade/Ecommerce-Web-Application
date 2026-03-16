package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.User;
import com.ecommerce.payloads.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);

    UserDTO findByEmail(String email);
	 
	 

}
