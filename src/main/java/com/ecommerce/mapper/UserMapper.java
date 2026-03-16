package com.ecommerce.mapper;

import com.ecommerce.entities.User;
import com.ecommerce.payloads.UserDTO;

public class UserMapper {
	
	// Entity → DTO
    public static UserDTO mapToDTO(User user) {

        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());

        return dto;
    }

    // DTO → Entity
    public static User mapToEntity(UserDTO dto) {

        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setPassword(dto.getPassword()); 

        return user;
    }

}
