package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Session{
   private String sessionId;

public void setSessionId(String value) {
    this.sessionId = value;
}
@Id
public String getSessionId() {
    return this.sessionId;
}
   private Tutorial tutorial;
   
   @OneToOne(optional=false)
   public Tutorial getTutorial() {
      return this.tutorial;
   }
   
   public void setTutorial(Tutorial tutorial) {
      this.tutorial = tutorial;
   }
   
   private Set<Student> student;
   
   @OneToMany(mappedBy="session" )
   public Set<Student> getStudent() {
      return this.student;
   }
   
   public void setStudent(Set<Student> students) {
      this.student = students;
   }
   
   private Set<Review> review;
   
   @OneToMany
   public Set<Review> getReview() {
      return this.review;
   }
   
   public void setReview(Set<Review> reviews) {
      this.review = reviews;
   }
   
   private Bill bill;
   
   @OneToOne(optional=false)
   public Bill getBill() {
      return this.bill;
   }
   
   public void setBill(Bill bill) {
      this.bill = bill;
   }
   
   }
