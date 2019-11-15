package ca.mcgill.ecse321.tutoringsystem.dto;

public class ReviewDto {
	
		private String reviewId;
		private String comment;
		private int rating;

		public ReviewDto() {
		}
		
		//here we would want the student to get all his sessions

		public ReviewDto(String reviewId, String comment, int rating) {
			this.reviewId = reviewId;
			this.comment = comment;
			this.rating = rating;
		}
		
		public String getReviewId() {
			return reviewId;
		}

		public String getComment() {
			return comment;
		}

		public int getRating() {
			return rating;
		}
}
