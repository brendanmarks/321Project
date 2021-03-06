package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

	/**
	 * Set tutor
	 * @param tutors
	 */
	public void setTutor(Set<Tutor> tutors) {
		this.tutor = tutors;
	}

	private Set<Session> session;

	/**
	 * Get Sessions
	 * @return
	 */
	@OneToMany(mappedBy="tutorial" )
	public Set<Session> getSession() {
		return this.session;
	}

	/**
	 * Set sessions
	 * @param sessions
	 */
	public void setSession(Set<Session> sessions) {
		this.session = sessions;
	}

	private Course course;

	/**
	 * Get course
	 * @return
	 */
	@ManyToOne(optional=false)
	public Course getCourse() {
		return this.course;
	}

	/**
	 * Set course
	 * @param course
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

}
