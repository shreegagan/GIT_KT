package com.mentormate.mentormate.services;

import java.util.List;

import com.mentormate.mentormate.dto.OkrDTO;
import com.mentormate.mentormate.entities.KeyResults;
import com.mentormate.mentormate.entities.Objectives;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.KeyResultsModel;
import com.mentormate.mentormate.models.ObjectivesModel;

public interface OKRService {

	//Add a new objective
	Objectives createObjectives(Users users,String objective);

	// update existing objective
	Objectives updateObjective(ObjectivesModel objectiveModel);

	// Fetch objective based on id
	Objectives getObjective(long id);

	// Fetch all objectives assigned to a mentee
	List<OkrDTO> getAllObjectivesAndKeyResultsForMentee(Users mentee);

	//Add a new KeyResult
	KeyResults createKeyResults(Objectives objectives,String keyResult);

	// Update existing KeyResult
	KeyResults updateKeyResults(KeyResultsModel keyResultsModel);

	// Fetch KeyResult based on id
	KeyResults getKeyResults(long id);

	// Fetch all key-results for an objective
	List<KeyResults> getAllKeyResultsForObjective(Objectives objective);

}