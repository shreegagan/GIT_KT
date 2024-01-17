package com.mentormate.mentormate.models;

import java.util.List;

import com.mentormate.mentormate.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersModel {
	private long id;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String confirmPassword;
	private String designation;
	private byte[] profilePicture;
	private List<String> roles;

	// Constructor to initialize all fields
	public UsersModel(Users user) {
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.password = user.getPassword();
		this.designation = user.getDesignation();
		this.roles = user.getRoles();
		this.profilePicture = user.getProfilePicture();
	}

}
