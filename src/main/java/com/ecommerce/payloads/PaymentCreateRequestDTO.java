package com.ecommerce.payloads;

import jakarta.validation.constraints.NotNull;

public class PaymentCreateRequestDTO {
	
	@NotNull(message = "User ID is required")
    private Long userId;

    public PaymentCreateRequestDTO() {}

    public PaymentCreateRequestDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
	

}
