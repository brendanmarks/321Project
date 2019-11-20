package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Review{
   private String reviewId;

public void setReviewId(String value) {
    this.reviewId = value;
}
@Id
public String getReviewId() {
    return this.reviewId;
}
private String comment;

public void setComment(String value) {
    this.comment = value;
}
public String getComment() {
    return this.comment;
}
private int rating;

public void setRating(int value) {
    this.rating = value;
}
public int getRating() {
    return this.rating;
}
}
