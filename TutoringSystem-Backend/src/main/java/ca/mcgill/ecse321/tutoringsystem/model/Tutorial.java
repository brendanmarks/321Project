package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
   private Tutor tutor;
   
   @ManyToOne(optional=false)
   public Tutor getTutor() {
      return this.tutor;
   }
   
   public void setTutor(Tutor tutor) {
      this.tutor = tutor;
   }
   
   private Set<Course> course;
   
   @OneToMany(mappedBy="tutorial" )
   public Set<Course> getCourse() {
      return this.course;
   }
   
   public void setCourse(Set<Course> courses) {
      this.course = courses;
   }
   
   private Session session;
   
   @OneToOne(mappedBy="tutorial" , optional=false)
   public Session getSession() {
      return this.session;
   }
   
   public void setSession(Session session) {
      this.session = session;
   }
   
   }
