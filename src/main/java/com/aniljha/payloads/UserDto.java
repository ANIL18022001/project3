package com.aniljha.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	//@NotBlank
//	@NotNull
	@NotEmpty
	@Size(min=4, message="UserName must be min of 4 characters!! ")
	private String name;
	@Email(message="Email address is not valid")
	private String email;
	@NotEmpty
//	@Size(min=3,max=10,message="Passowrd must be min of 3 chars and max of 10 chars!!")
//	@Pattern(regexp="!@#$%&*()-+=^.",message=" 1: It contains at least 8 characters and at most 20 characters."
//			+ "2:It contains at least one digit. 3: It contains at least one upper case alphabet."
//			+ "4: It contains at least one lower case alphabet."
//			+ "5:It contains at least one special character which includes"
//			+ "6:It doesn’t contain any white space.")
	private String password;
	@NotEmpty
	@Size(min=15,max=100,message="About must be min of 15 chars and max of 100 chars!!")
	private String about;
}
