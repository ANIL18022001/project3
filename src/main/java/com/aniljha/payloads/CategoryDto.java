package com.aniljha.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class CategoryDto {
	private Integer categoryId;
	@NotEmpty
	@Size(min=10 , message="Title name must be min character of 10")
	private String categoryTitle;
	@NotEmpty(message="not blank ")
	@Size(min=10,message="description size minimum 10 character ")
	private String categoryDescription;
}
