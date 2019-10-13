package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class Tutorial{
   private String id;

public void setId(String value) {
    this.id = value;
}
@Id
public String getId() {
    return this.id;
}
   private Set<Tutor> tutor;
   
   @ManyToMany
   public Set<Tutor> getTutor() {
      return this.tutor;
   }
   
   public void setTutor(Set<Tutor> tutors) {
      this.tutor = tutors;
   }
   
   private Session session;
   
   @OneToOne(mappedBy="tutorial" )
   public Session getSession() {
      return this.session;
   }
   
   public void setSession(Session session) {
      this.session = session;
   }
   
   private Course course;
   
   @ManyToOne(optional=false)
   public Course getCourse() {
      return this.course;
   }
   
   public void setCourse(Course course) {
      this.course = course;
   }
   
   }
