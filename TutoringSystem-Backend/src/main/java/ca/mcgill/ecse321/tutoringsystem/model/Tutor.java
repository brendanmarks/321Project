package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import java.sql.Time;

@Entity
public class Tutor extends Person{
   private Set<Tutorial> tutorial;
   
   @OneToMany(mappedBy="tutor" )
   public Set<Tutorial> getTutorial() {
      return this.tutorial;
   }
   
   public void setTutorial(Set<Tutorial> tutorials) {
      this.tutorial = tutorials;
   }
   
   private double hourlyRate;

public void setHourlyRate(double value) {
    this.hourlyRate = value;
}
public double getHourlyRate() {
    return this.hourlyRate;
}
private Time availability;

public void setAvailability(Time value) {
    this.availability = value;
}
public Time getAvailability() {
    return this.availability;
}
}
