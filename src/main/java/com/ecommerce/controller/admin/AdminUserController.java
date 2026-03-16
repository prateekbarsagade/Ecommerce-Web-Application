package com.ecommerce.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payloads.UserDTO;
import com.ecommerce.services.UserService;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
	
	private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }
    
    
 // Get All Users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
    
 // Get User By ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {

        UserDTO user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }
    
    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }


}
