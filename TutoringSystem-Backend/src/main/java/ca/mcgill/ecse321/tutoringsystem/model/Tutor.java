package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Tutor extends Person{
	private Set<Tutorial> tutorial;

	@ManyToMany(mappedBy="tutor" )
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
}
