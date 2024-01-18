package com.mentormate.mentormate.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mentormate.mentormate.dto.OkrDTO;
import com.mentormate.mentormate.entities.KeyResults;
import com.mentormate.mentormate.entities.Objectives;
import com.mentormate.mentormate.entities.Users;
import com.mentormate.mentormate.models.KeyResultsModel;
import com.mentormate.mentormate.models.ObjectivesModel;
import com.mentormate.mentormate.repositories.KeyResultsRepository;
import com.mentormate.mentormate.repositories.ObjectivesRepository;

import java.util.ArrayList;
import java.util.Collections;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OKRServiceImpl implements OKRService {
	
	private static final Logger logger = LoggerFactory.getLogger(OKRServiceImpl.class);

	@Autowired
	ObjectivesRepository objectivesRepository;

	@Autowired
	KeyResultsRepository keyResultsRepository;

	// Add a new objective
	@Override
	public Objectives createObjectives(Users user, String objective) {
		
		logger.debug("entering createObjectives method");
		logger.info("Creating objectives for user: {} (objective: {})", user.getEmail(), objective);
		
		Objectives objectives = new Objectives();
		objectives.setUser(user);
		objectives.setObjective(objective);
		
		// adding information about current method into the logger object
//		logger.info("objectives created");
//		return objectivesRepository.save(objectives);
		
		try {
	        Objectives savedObjectives = objectivesRepository.save(objectives);
	        logger.info("Objectives created successfully with ID: {}", savedObjectives.getId());
	        return savedObjectives;
	    } catch (Exception e) {
	        logger.error("Failed to create objectives: {}", e.getMessage());
	        throw new RuntimeException("Failed to create objectives", e); 
	    }
	}

	// update existing objective
	@Override
	public Objectives updateObjective(ObjectivesModel objectiveModel) {
		return objectivesRepository.save(new Objectives(objectiveModel.getUser(), objectiveModel.getObjective()));
	}

	// Fetch objective based on id
	@Override
	public Objectives getObjective(long id) {
		// adding information about current method into the logger object
		logger.info("getting objective based on id");
		return objectivesRepository.getReferenceById(id);
	}

	// Fetch all objectives assigned to a mentee
	@Override
	public List<OkrDTO> getAllObjectivesAndKeyResultsForMentee(Users mentee) {
		
		logger.info("Retrieving all objectives and key results for mentee: {}", mentee.getEmail());
		
		try {
	        List<Objectives> objectives = objectivesRepository.findAllByUser(mentee);
	        List<OkrDTO> okrs = new ArrayList<>();

	        for (Objectives objective : objectives) {
	            List<KeyResults> listofOkr = getAllKeyResultsForObjective(objective);
	            List<String> listOfStr = listofOkr.stream().map(kr -> kr.getKeyResult()).toList();
	            okrs.add(new OkrDTO(objective.getObjective(), listOfStr));
	        }

	        logger.info("Retrieved {} OKRs for mentee: {}", okrs.size(), mentee.getEmail());
	        return okrs; // Return the list of OKR DTOs

	    } catch (Exception e) {
	        logger.error("Failed to retrieve objectives and key results for mentee: {}", mentee.getId(), e);
	        throw new RuntimeException("Failed to retrieve objectives and key results", e); 
	    }
	}

	// Add a new KeyResult
	@Override
	public KeyResults createKeyResults(Objectives objectives, String keyResult) {
		KeyResults keyResults = new KeyResults();
		keyResults.setObjective(objectives);
		keyResults.setKeyResult(keyResult);
		
		try {		
			// adding information about current method into the logger object
			logger.info("key-result created");
			return keyResultsRepository.save(keyResults);
		} catch (Exception e) {
			logger.error("failed to create key-results {}", e.getMessage());
			throw new RuntimeException("Failed to create objectives", e); 
		}
	}

	// Update existing KeyResult
	@Override
	public KeyResults updateKeyResults(KeyResultsModel keyResultsModel) {
		return keyResultsRepository
				.save(new KeyResults(keyResultsModel.getObjective(), keyResultsModel.getKeyResult()));
	}

	// Fetch KeyResult based on id
	@Override
	public KeyResults getKeyResults(long id) {
		
		// adding information about current method into the logger object
		logger.info("getting key-result based on id: {}", id);
		return keyResultsRepository.getReferenceById(id);
	}

	// Fetch all key-results for an objective
	@Override
	public List<KeyResults> getAllKeyResultsForObjective(Objectives objective) {
		if (objective != null) {
			// adding information about fetching key-results for an objective
			logger.info("getting all key-results for objective {}", objective.getObjective());
			return keyResultsRepository.findAllByObjective(objective);
		}else {
			return Collections.emptyList();
		}
	}
}
