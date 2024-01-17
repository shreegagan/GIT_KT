package com.mentormate.mentormate.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.management.relation.RoleNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mentormate.mentormate.entities.PasswordResetToken;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.UsersModel;
import com.mentormate.mentormate.repositories.TokenRepository;
import com.mentormate.mentormate.repositories.UsersRepository;

//Service implementation for handling user-related operations
@Service
public class UsersServiceImpl implements UsersService {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

	// Injecting UsersRepository dependency

	@Autowired
	private UsersRepository usersRepository;

	// Retrieve all users from the database
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	TokenRepository tokenRepository;

	@Override
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}

	// Create a new user based on the provided user model
	// Throws RoleNotFoundException if the specified role is not found
	@Override
	public Users createUsers(UsersModel usersModel) throws RoleNotFoundException {

		Users existingUser = usersRepository.findByEmail(usersModel.getEmail());

		// Check if a user with the given email already exists
		if (existingUser != null) {
			throw new RuntimeException("User with the provided email already exists");

		}
		// Create a new Users entity using the data from the UsersModel
		Users user = new Users(usersModel.getEmail(), StringUtils.capitalize(usersModel.getFirstName()),
				usersModel.getLastName(), passwordEncoder.encode(usersModel.getPassword()), usersModel.getDesignation(),
				usersModel.getRoles(), usersModel.getProfilePicture());
		
		// adding information about current method into the logger object
		logger.info("User created successfully with ID: {}", user.getId());

		// Save the new user to the database
		return usersRepository.save(user);
	}

	@Override
	public boolean isUserExists(String email) {
		// Check if a user with the given email already exists in the database
		return usersRepository.findByEmail(email) != null;
	}

	@Override
	public Users findByEmail(String username) {
		
		// adding information about current method into the logger object
		logger.info("finding user by username: {}", username);
		return usersRepository.findByEmail(username);
	}

	@Override
	public Users getUserById(long userId) {
		// adding information about current method into the logger object
		logger.info("finding user by id: {}", userId);
		return usersRepository.getReferenceById(userId);
	}
	
	@Override
    public Users findById(long id) {
		// adding information about current method into the logger object
		logger.info("fetching record based on id: {}", id);
        return usersRepository.findById(id).orElse(null);
    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = usersRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new HashSet<GrantedAuthority>());
	}

	public Users save(UsersModel userModel) {
		Users user = new Users();
		user.setEmail(userModel.getEmail());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setDesignation(userModel.getDesignation());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		return usersRepository.save(user);
	}

	public String sendEmail(Users user) {
		try {
			String resetLink = generateResetToken(user);
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("mentormate.help@gmail.com");// input the senders email ID
			msg.setTo(user.getEmail());
			msg.setSubject("Welcome To MentorMate");
			msg.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
					+ "Regards \n" + "MentorMate");
			javaMailSender.send(msg);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String generateResetToken(Users user) {
		try {
			UUID uuid = UUID.randomUUID();
			LocalDateTime currentDateTime = LocalDateTime.now();
			LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);

			PasswordResetToken resetToken = new PasswordResetToken();
			resetToken.setUser(user);
			resetToken.setToken(uuid.toString());
			resetToken.setExpiryDateTime(expiryDateTime);
			resetToken.setUser(user);

			PasswordResetToken savedToken = tokenRepository.save(resetToken);

			if (savedToken != null) {
				String endpointUrl = "http://localhost:8080/resetPassword";
				return endpointUrl + "/" + resetToken.getToken();
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public boolean hasExipred(LocalDateTime expiryDateTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return expiryDateTime.isAfter(currentDateTime);
	}

}
