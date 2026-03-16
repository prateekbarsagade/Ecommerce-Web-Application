package com.ecommerce.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class CategoryDTO {
	
	private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;

	public CategoryDTO(Long id,
			@NotBlank(message = "Category name is required") @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters") String name,
			@NotBlank(message = "Description is required") @Size(max = 200, message = "Description cannot exceed 200 characters") String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public CategoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
    

}
