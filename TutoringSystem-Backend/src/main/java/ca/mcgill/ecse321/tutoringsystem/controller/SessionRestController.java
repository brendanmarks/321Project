package ca.mcgill.ecse321.tutoringsystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.tutoringsystem.dto.CourseDto;
import ca.mcgill.ecse321.tutoringsystem.dto.SessionDto;
import ca.mcgill.ecse321.tutoringsystem.dto.StudentDto;
import ca.mcgill.ecse321.tutoringsystem.dto.TutorDto;
import ca.mcgill.ecse321.tutoringsystem.dto.TutorialDto;
import ca.mcgill.ecse321.tutoringsystem.model.Bill;
import ca.mcgill.ecse321.tutoringsystem.model.Course;
import ca.mcgill.ecse321.tutoringsystem.model.Session;
import ca.mcgill.ecse321.tutoringsystem.model.Student;
import ca.mcgill.ecse321.tutoringsystem.model.Tutor;
import ca.mcgill.ecse321.tutoringsystem.model.Tutorial;
import ca.mcgill.ecse321.tutoringsystem.service.TutoringSystemService;

@CrossOrigin(origins = "*")
@RestController
public class SessionRestController {
	@Autowired
	TutoringSystemService service;

	/**
	 * Create session endpoint
	 * @param sessionId
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @param tutorialId
	 * @param studentName
	 * @return session dto
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { 
			"/sessions/{sessionId}", 
			"/sessions/{sessionId}/" 
	})
	public SessionDto createSession(
			@PathVariable ("sessionId") String sessionId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
			@RequestParam Date date,         
			@RequestParam(name = "tutorialId") String tutorialId,
			@RequestParam(name = "studentName") String studentName
			)	throws IllegalArgumentException {

		Integer billId = Integer.parseInt(sessionId);

		Tutorial tutorial = service.getTutorial(tutorialId);

		Student firstStudent = service.getStudent(studentName);

		Bill bill = service.createBill(false, billId);

		Session session = service.createSession(sessionId, Time.valueOf(startTime), Time.valueOf(endTime), date, bill, tutorial, firstStudent);
		System.out.println(session.getDate());
		return convertSessionToDto(studentName, session);
	}

	/**
	 * Converts a session obj to dto
	 * @param name
	 * @param ss
	 * @return session dto
	 */
	private SessionDto convertSessionToDto(String name, Session ss) {
		if (name == null || ss == null) {
			throw new IllegalArgumentException("This student or session does not exist.");
		}	
		Set<Student> students = ss.getStudent();

		ArrayList<StudentDto> sessionStudentDtos = new ArrayList<>();

		if(students.size() > 0)	{

			for(java.util.Iterator<Student> iterate = students.iterator(); iterate.hasNext();) {
				Student s = iterate.next();
				StudentDto sDto = StudentRestController.convertStudentToDto(s);
				sessionStudentDtos.add(sDto);
			}
		}	else if(students.size() == 0 || students == null)	{

			Student student = service.getStudent(name);
			StudentDto firstStudent = StudentRestController.convertStudentToDto(student);
			sessionStudentDtos.add(firstStudent);
		}
		Tutor tutor = ss.getTutorial().getTutor().iterator().next();
		Course course = ss.getTutorial().getCourse();
		SessionDto sessionDto = new SessionDto(ss.getSessionId(), ss.getDate(), ss.getStartTime(), ss.getEndTime(), sessionStudentDtos, new TutorialDto(new TutorDto(tutor.getName(), tutor.getEmail(), tutor.getUsername(), tutor.getPassword(), null, tutor.getHourlyRate()), new CourseDto(course.getCourseId(), course.getCourseName()), null));
		return sessionDto;
	}
	
	/**
	 * Get all sessions endpoint
	 * @return list of sessions dtos
	 */
	@GetMapping(value = {"/sessions","/sessions/"})
	public List<SessionDto> getAllSessions(){
		try {
			List<Session> allSessions = service.getAllSessions();

			List<SessionDto> allSessionsAsDto = new ArrayList<>();
			for(Session s : allSessions) {
				ArrayList<StudentDto> students = new ArrayList<>();
				for (Student stu : s.getStudent()) {
					students.add(new StudentDto(stu.getName(), stu.getEmail(), stu.getUsername(), stu.getPassword(), null));
				}
				Tutor tutor = s.getTutorial().getTutor().iterator().next();
				Course course = s.getTutorial().getCourse();
				allSessionsAsDto.add(new SessionDto(s.getSessionId(), s.getDate(), s.getStartTime(), s.getEndTime(), students, new TutorialDto(new TutorDto(tutor.getName(), tutor.getEmail(), tutor.getUsername(), tutor.getPassword(), null, tutor.getHourlyRate()), new CourseDto(course.getCourseId(), course.getCourseName()), null)));
			}
			return allSessionsAsDto;
		}catch(Exception e) {
			System.out.println("Could not get all sessions");
			return null;
		}
	}

	/**
	 * Get all sessions of a student by username
	 * @param studentUsername
	 * @return list of sessions dtos
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/sessions/student/{studentUsername}","/sessions/student/{studentUsername}/"})
	public List<SessionDto> getAllSessionsOfStudent(@PathVariable("studentUsername") String studentUsername) throws IllegalArgumentException{
		try {
			List<Session> allSessions = service.getAllSessions();
			List<SessionDto> allSessionsOfStudent = new ArrayList<>();
			ArrayList<StudentDto> students = new ArrayList<>();

			for(Session s: allSessions) {
				Set<Student> sessionStudents = s.getStudent();
				for (Student student: sessionStudents) {
					if(student.getName().equals(studentUsername)) {
						for (Student stu : s.getStudent()) {
							students.add(new StudentDto(stu.getName(), stu.getEmail(), stu.getUsername(), stu.getPassword(), null));
						}
						Tutor tutor = s.getTutorial().getTutor().iterator().next();
						Course course = s.getTutorial().getCourse();
						allSessionsOfStudent.add(new SessionDto(s.getSessionId(), s.getDate(), s.getStartTime(), s.getEndTime(), students, new TutorialDto(new TutorDto(tutor.getName(), tutor.getEmail(), tutor.getUsername(), tutor.getPassword(), null, tutor.getHourlyRate()), new CourseDto(course.getCourseId(), course.getCourseName()), null)));
					}
				}
			}
			return allSessionsOfStudent;	
		}catch(Exception e) {
			System.out.println("Could not get all sessions of "+studentUsername);
			return null;
		}
	}
	
	/**
	 * Get session by id
	 * @param sessionID
	 * @return session dto
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/sessions/{sessionID}","/sessions/{sessionID}/"})
	public SessionDto getSessionById(@PathVariable String sessionID) throws IllegalArgumentException {
		try {
			Session session = service.getSession(sessionID);
			Student student = getStudentFromSet(session.getStudent());
			StudentDto studentDto = StudentRestController.convertStudentToDto(student);
			SessionDto sessionDto = convertSessionToDto(studentDto.getUsername(),session);
			return sessionDto;
		}catch(Exception e){
			System.out.println("Could not get session "+sessionID);
			return null;
		}
	}
	
	/**
	 * Get student from a set of students
	 * @param studentset
	 * @return student
	 */
	private Student getStudentFromSet(Set<Student> studentset) {
		Student student = null;
		if(studentset.size() == 0) {
			return student; //student will be null
		} else if(studentset.size() > 1) {
			for(java.util.Iterator<Student> iterate = studentset.iterator(); iterate.hasNext();) {
				student = iterate.next();
			}
		} else {
			student = studentset.iterator().next();
		}
		return student;//default
	}
	
	/**
	 * Delete a session endpoint
	 * @param sessionID
	 * @return status code (200 success, 500 error)
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = {"/sessions/{sessionID}","/sessions/{sessionID}/"})
	public int deleteSessionByID(@PathVariable("sessionID") String sessionID) 
			throws IllegalArgumentException{
		try{
			return service.deleteSession(sessionID);
		}catch(Exception e) {
			System.out.println("Could not delete session "+ sessionID);
			return 500;
		}
	}


}
