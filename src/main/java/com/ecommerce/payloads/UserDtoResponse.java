package com.ecommerce.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDtoResponse {
	
	private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

}
