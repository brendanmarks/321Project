package ca.mcgill.ecse321.tutoringsystem.dto;

public class ReviewDto {
	
		private String reviewId;
		private String comment;
		private int rating;
		
		/**
		 * Default constructor
		 */
		public ReviewDto() {
		}
		
		/**
		 * Review Dto constructor
		 * @param reviewId
		 * @param comment
		 * @param rating
		 */
		public ReviewDto(String reviewId, String comment, int rating) {
			this.reviewId = reviewId;
			this.comment = comment;
			this.rating = rating;
		}
		
		/**
		 * Get review id
		 * @return review id
		 */
		public String getReviewId() {
			return reviewId;
		}
		
		/**
		 * Get review comment
		 * @return comment
		 */
		public String getComment() {
			return comment;
		}
		
		/**
		 * Get review rating
		 * @return rating
		 */
		public int getRating() {
			return rating;
		}
}
