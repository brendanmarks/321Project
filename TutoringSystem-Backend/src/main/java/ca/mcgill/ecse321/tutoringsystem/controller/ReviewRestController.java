package ca.mcgill.ecse321.tutoringsystem.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.tutoringsystem.dto.ReviewDto;
import ca.mcgill.ecse321.tutoringsystem.model.Review;
import ca.mcgill.ecse321.tutoringsystem.model.Session;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;
import ca.mcgill.ecse321.tutoringsystem.service.TutoringSystemService;

@CrossOrigin(origins = "*")
@RestController
public class ReviewRestController {
	
	@Autowired
	TutoringSystemService service;
	
	/**
	 * Create Review endpoint
	 * @param sessionId
	 * @param reviewId
	 * @param comment
	 * @param rating
	 * @param name
	 * @return review dto
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { 
			"/reviews/{sessionId}/{reviewId}", 
			"/reviews/{sessionId}/{reviewId}/" 
	})
	public ReviewDto createReview(

			@PathVariable("sessionId") String sessionId, 		
			@PathVariable("reviewId") String reviewId,

			@RequestParam(name = "comment") String comment, 		//For now the comment must be one string... Must change the method to accept multi word...or use a text file as param after creating the review to then call setComment on the review and [ass thet text file ....   -Dom
			@RequestParam(name = "rating") int rating,				
			@RequestParam(name = "studentName") String name			//The name of the student that will be posting the review 

			)	throws IllegalArgumentException {

		//First we save to persistence the new review
		Review review = service.createReview(reviewId, comment, rating);

		//Fetch the session that has just been completed
		Session session = service.getSession(sessionId);

		//Check if session already has reviews saved
		Set<Review> reviews = session.getReview();

		//If the Review field of the specified session instance hasn't been instantiated, we create a new empty Set<Review>
		if(reviews.equals(null) || reviews == null || reviews.size() == 0)	{

			reviews = new HashSet<Review>();

		}
		//If there already was an existing set of reviews saved for this specified session instance, then we just add the new created review to the set
		reviews.add(review);

		//Assign the new set of reviews to the session instance
		session.setReview(reviews);

		//Call the service method that will update the saved session and add the new reviews
		session = service.updateSession(session);		//method at line 232 of service class

		return convertReviewToDto(review);	
	}
	
	/**
	 * Get review by id endpoint
	 * @param reviewId
	 * @return review dto
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { 
			"/reviews/{reviewId}",
			"/reviews/{reviewId}/" 
	})
	public ReviewDto getReviewById(@PathVariable("reviewId") String reviewId)	
			throws IllegalArgumentException {

		//Get the instances that are required from database
		Review review = service.getReview(reviewId);

		//Create a review DTO. It needs a sessionDto as a parameter	
		ReviewDto reviewDto = convertReviewToDto(review);

		return reviewDto;
	}
	
	/**
	 * Get reviews of a tutor
	 * @param tutorName
	 * @return list of review dtos
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { 
			"/reviews",
			"/reviews/" 
	})
	public List<ReviewDto> getReviewsOfTutor(@RequestParam(name = "tutorName") String tutorName)
			throws IllegalArgumentException {

		//Instantiate to null the returned variable
		List<ReviewDto> tutorReviewsDtos = new ArrayList<ReviewDto>();

		//Retrieve from database the saved instance
		Tutor tutor = service.getTutor(tutorName);

		//Exit this function if the tutor was not fund in the database
		if(tutor == null || tutor.equals(null))	{
			System.out.println("Error: The specified Tutor was not found in the system database. The tutor '"+tutorName+"' does not exist or the specified name ("+tutorName+") might be invalid/contains a spelling error.");
			return tutorReviewsDtos;
		}

		//Get all tutorials offered by the specified tutor
		Set<Tutorial> tutorTutorials = tutor.getTutorial();

		//The specified tutor does not have any assigned tutorials
		if (tutorTutorials == null || tutorTutorials.equals(null) || tutorTutorials.size() == 0)	{
			System.out.println("The specified tutor ("+tutorName+") does not have any assigned tutorials yet. Therefore, he does not have any reviews to their name at the moment.");
			return tutorReviewsDtos;
		}


		//The specified tutor only has one assigned Tutorial, thus we do not need to iterate in a for loop
		if (tutorTutorials.size() == 1)	{


			//We retrieve the only tutorial in the set 
			java.util.Iterator<Tutorial> iterateT = tutorTutorials.iterator();
			Tutorial tutorial = iterateT.next();
			Set<Session> sessions = tutorial.getSession();


			//The tutor has one tutorial, but no assigned sessions
			if (sessions == null || sessions.equals(null) || sessions.size() == 0)	{
				System.out.println("One tutorial instance was found for the specified tutor ("+tutorName+").However, they have not yet been assigned any sessions yet. Therefore, he does not have any reviews to their name at the moment.");
				return tutorReviewsDtos;
			}


			//The tutor has one tutorial, with one assigned session
			if (sessions.size() == 1)	{


				java.util.Iterator<Session> iterateS = sessions.iterator();
				Session session = iterateS.next();
				Set<Review> reviews = session.getReview();


				//The tutor has one tutorial, with one assigned session, but no reviews have been created
				if (reviews == null || reviews.equals(null) || reviews.size() == 0)	{


					System.out.println("One session instance for the "+tutorial.getCourse()+" tutorial was found for the specified tutor ("+tutorName+").However, this tutor did not receive any reviews at the moment.");
					return tutorReviewsDtos;


					//The tutor has one tutorial, with one assigned session and one or multiple review instance(s) have been found	
				} else	{
					for(java.util.Iterator<Review> iterateR = reviews.iterator(); iterateR.hasNext();) {
						Review review = iterateR.next();
						ReviewDto rDto = convertReviewToDto(review);
						tutorReviewsDtos.add(rDto);	
					} 
					return tutorReviewsDtos;
				}


				//The tutor has one tutorial, with many assigned session instances
			} else	{
				//We iterate through the multiple session instances of the tutorial
				for(java.util.Iterator<Session> iterateS = sessions.iterator(); iterateS.hasNext();) {
					Session session = iterateS.next();

					Set<Review> reviews = session.getReview();

					//Some sessions might not have any reviews related to them. In this case, we skip to the next loop iteration (continue)
					if (reviews == null || reviews.equals(null) || reviews.size() == 0)	{
						continue;


						//One or multiple reviews were found. We iterate through the Set of reviews, convert them to Dtos and add them to the List<ReviewDto>
					} else	{
						for(java.util.Iterator<Review> iterateR = reviews.iterator(); iterateR.hasNext();) {
							Review review = iterateR.next();
							ReviewDto rDto = convertReviewToDto(review);
							tutorReviewsDtos.add(rDto);	
						} 
					}
				} 
				return tutorReviewsDtos;
			}	

			//The specified tutor has multiple assigned Tutorials
		} else {
			//We iterate through the multiple tutorials assigned to the specified tutor
			for(java.util.Iterator<Tutorial> iterateT = tutorTutorials.iterator(); iterateT.hasNext();) {
				Tutorial tutorial = iterateT.next();

				/* We repeat the same steps in this for loop as the previous steps (i.e. when the tutor had only one tutorial), but we skip */

				Set<Session> sessions = tutorial.getSession();

				//Some tutorial assigned to the specified tutor might not have any session instance. In this case, we skip this loop iteration (continue)
				if (sessions == null || sessions.equals(null) || sessions.size() == 0)	{
					continue;
				}

				//One or multiple session instances were found
				else {

					//We iterate through all the multiple session instances
					for(java.util.Iterator<Session> iterateS = sessions.iterator(); iterateS.hasNext();) {
						Session session = iterateS.next();

						Set<Review> reviews = session.getReview();

						//Some session instances might not have any reviews related to them. In this case, we skip this loop iteration (continue)
						if (reviews == null || reviews.equals(null) || reviews.size() == 0)	{
							continue;
						}

						//One or multiple reviews were found. We iterate through the Set of reviews, convert them to Dtos and add them to the List<ReviewDto>
						else	{
							//We iterate through the Set of reviews, convert them to Dtos and add them to the List<ReviewDto>
							for(java.util.Iterator<Review> iterateR = reviews.iterator(); iterateR.hasNext();) {
								Review review = iterateR.next();
								ReviewDto rDto = convertReviewToDto(review);
								tutorReviewsDtos.add(rDto);	
							} 
						}
					}

				}
			}
			if(tutorReviewsDtos.size() == 0)	{
				System.out.println("After completion of this request, although the specified tutor has many assigned tutorials with many session instances, no review instances were found in the system database. Either none exist, or some unknow error occured during the execution of this request.");
			}
		}
		return tutorReviewsDtos;		
	}
	
	/**
	 * Converts a review obj to dto
	 * @param r
	 * @return review dto
	 */
	private ReviewDto convertReviewToDto(Review r) {
		if (r == null) {
			throw new IllegalArgumentException("This review does not exist.");
		}		
		ReviewDto reviewDto = new ReviewDto(r.getReviewId(), r.getComment(), r.getRating());
		return reviewDto;
	}

}
