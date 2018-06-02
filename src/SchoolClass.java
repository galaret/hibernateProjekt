import java.util.HashSet;
import java.util.Set;

public class SchoolClass implements java.io.Serializable {

	private long id;
	private int startYear;
	private int currentYear;
	private String profile;
	private Set<Student> students;

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	public void addTeacher( Teacher tempTeacher) {
		teachers.add(tempTeacher);
	}

	private Set<Teacher> teachers;

	public SchoolClass() {
		students = new HashSet<Student>();
	}
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public void addStudent(Student newStudent) {
		students.add(newStudent);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String toString() {
		return "Class: " + profile + " (Started: " + getStartYear() + ", Current year: " + getCurrentYear() + ")";
	}


}