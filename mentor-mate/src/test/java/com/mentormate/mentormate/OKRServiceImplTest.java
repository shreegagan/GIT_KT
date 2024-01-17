package com.mentormate.mentormate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mentormate.mentormate.entities.KeyResults;
import com.mentormate.mentormate.entities.Objectives;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.repositories.KeyResultsRepository;
import com.mentormate.mentormate.repositories.ObjectivesRepository;

@SpringBootTest
class OKRServiceImplTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Autowired
	ObjectivesRepository objectivesRepository;
	
	@Autowired
	KeyResultsRepository keyResultsRepository;
	
	@Test
	void testCreateObjective() {
		List<String> roles = new ArrayList<>();
        roles.add("mentor");
		Users user = new Users("google@gmail.com","jim","halpert","hello","manager",roles);
		Objectives objective = objectivesRepository.save(new Objectives(user,"share"));
		assertEquals("share", objective.getObjective());
	}
	
	@Test
	void testCreateKeyResult() {
		List<String> roles = new ArrayList<>();
        roles.add("mentor");
		Users user = new Users("yahoo@gmail.com","pam","beasely","xyz","manager",roles);
		Objectives objective = new Objectives(user,"dare");
		KeyResults keyResults = keyResultsRepository.save(new KeyResults(objective,"share you ideas"));
		assertEquals("share you ideas", keyResults.getKeyResult());
	}

}
