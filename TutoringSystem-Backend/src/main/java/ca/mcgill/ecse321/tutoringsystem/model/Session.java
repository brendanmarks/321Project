package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Null;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.sql.Time;
import java.sql.Date;

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

@ManyToOne(optional=false)
public Tutorial getTutorial() {
   return this.tutorial;
}

public void setTutorial(Tutorial tutorial) {
   this.tutorial = tutorial;
}

private Set<Student> student;

@ManyToMany
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

private Time startTime;

public void setStartTime(Time value) {
    this.startTime = value;
}
public Time getStartTime() {
    return this.startTime;
}
private Time endTime;

public void setEndTime(Time value) {
    this.endTime = value;
}
public Time getEndTime() {
    return this.endTime;
}
private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
   private Bill bill;
   
   @ManyToOne
   public Bill getBill() {
      return this.bill;
   }
   
   public void setBill(Bill bill) {
      this.bill = bill;
   }
   
   }
