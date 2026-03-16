package com.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.entities.User;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.payloads.UserDTO;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.services.UserService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = UserMapper.mapToEntity(userDTO);
        
     // set additional fields
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return UserMapper.mapToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return UserMapper.mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        userRepository.delete(userId);
    }

    @Override
    public UserDTO findByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with email " + email);
        }

        return UserMapper.mapToDTO(user);
    }

}
