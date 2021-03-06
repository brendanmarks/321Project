package ca.mcgill.ecse321.tutoringsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.tutoringsystem.model.Review;

public interface ReviewRepository extends CrudRepository<Review, String> {
	Review findReviewByReviewId(String reviewId);
}
