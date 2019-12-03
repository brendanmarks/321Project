package ca.mcgill.ecse321.tutoringsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;

public interface TutorialRepository extends CrudRepository<Tutorial, String> {
	Tutorial findTutorialById(String id);
}
