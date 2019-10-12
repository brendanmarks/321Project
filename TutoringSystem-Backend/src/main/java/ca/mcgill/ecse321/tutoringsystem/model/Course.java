package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Course{
   private String courseId;

public void setCourseId(String value) {
    this.courseId = value;
}
@Id
public String getCourseId() {
    return this.courseId;
}
private String courseName;

public void setCourseName(String value) {
    this.courseName = value;
}
public String getCourseName() {
    return this.courseName;
}
   private Tutorial tutorial;
   
   @ManyToOne(optional=false)
   public Tutorial getTutorial() {
      return this.tutorial;
   }
   
   public void setTutorial(Tutorial tutorial) {
      this.tutorial = tutorial;
   }
   
   }
