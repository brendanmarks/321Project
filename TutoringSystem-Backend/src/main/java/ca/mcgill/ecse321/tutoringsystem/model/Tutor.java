package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

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
   
   }
