import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		main.printSchools();
		main.executeQueries();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void executeQueries() {
		findSchool("Johns School");
		deleteSchool("Johns School");
		findSchool("Johns School");
		countStudents();
		returnSchoolsWithClassesNumberHigherThanArgument(2);
		returnSchoolsWhichSelectedProfileAndCurrentYearGreaterOrEqualThanArgument("mat-fiz",2);
	}

	private void findAllSchooldSquery(){
		String hql = "FROM School";
		Query query = session.createQuery(hql);
		List result = query.list();
		System.out.println(result);
	}

	private void findSchool(String schoolName){
		String hql = "FROM School as school WHERE school.name='" + schoolName + "'";
		Query query = session.createQuery(hql);
		List result = query.list();
		System.out.println(result);
	}

	private void deleteSchool(String schoolName){
		String hql = "FROM School as school WHERE school.name='" + schoolName + "'";
		Query query = session.createQuery(hql);
		List<School> result = query.list();
		Transaction transaction = session.beginTransaction();
		for (School school : result){
			session.delete(school);
		}
		transaction.commit();
	}

	private void countStudents(){
		String hql = "FROM Student";
		Query query = session.createQuery(hql);
		List students = query.list();
		System.out.println("Students number " + students.size());
	}

	private void countTeachers(){
		String hql = "FROM Teacher";
		Query query = session.createQuery(hql);
		List students = query.list();
		System.out.println("Teachers  number " + students.size());
	}

	private void returnSchoolsWithClassesNumberHigherThanArgument(int numberOfClasses){
		String hql = "FROM School";
		Query query = session.createQuery(hql);
		List<School> schools = query.list();

		for (School school: schools){
			if (school.getClasses().size() > numberOfClasses){
				System.out.println(school);
			}
		}
	}

	private void returnSchoolsWhichSelectedProfileAndCurrentYearGreaterOrEqualThanArgument(String classProfile, int yearOfStudies){
		String hql = "SELECT s FROM School s INNER JOIN s.classes classes WHERE classes.profile = '" + classProfile + "' AND classes.currentYear>=" + String.valueOf(yearOfStudies);
		Query query = session.createQuery(hql);
		List schools = query.list();
		System.out.println(schools);
	}

	private void updateSchools(){
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 2);
		School school = (School) query.uniqueResult();
		Transaction transaction = session.beginTransaction();

		school.setAddress("Al Pokoju 145");
		session.update(school);
		transaction.commit();
	}


	private void addNewData() {
		Transaction transaction = session.beginTransaction();
		School newSchool = new School();
		newSchool.setName("Johns School");
		newSchool.setAddress("al Diagon Alley");

		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setProfile("mat-inf");

		Student student = new Student();
		student.setName("Albus");
		student.setSurname("Dumbledore");
		schoolClass.addStudent(student);

		newSchool.addClass(schoolClass);
		session.save(newSchool);
		transaction.commit();
	}

	private void addTeachers() {
		Teacher teacher1=new Teacher("Hermiona", "Granger");
		Teacher teacher2=new Teacher("Ron", "Wesley");
		String hql = "FROM SchoolClass as classes";
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list();
		for (SchoolClass s : results) {
			System.out.println("Adding: "+s+" to: "+teacher1);
			s.addTeacher(teacher1);
			System.out.println("Adding: "+s+" to: "+teacher2);
			s.addTeacher(teacher2);
		}
		Transaction transaction = session.beginTransaction();
		session.save(teacher1);
		session.save(teacher2);
		transaction.commit();

	}

	private void printSchools() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		System.out.println("### Schools and classes");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		for (School s : schools) {
			System.out.println(s);
			for (SchoolClass schoolClass : s.getClasses()) {
				System.out.println("   " + schoolClass);
				for (Student student : schoolClass.getStudents()) {
					System.out.print("            " + student.getName());
					System.out.print(" " + student.getSurname());
					System.out.println(" (" + student.getPesel() + ")");
				}
			}
		}

		session.close();
		HibernateUtil.shutdown();
	}

	private void hibernateTest() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		for (School s : schools) {
			System.out.println(s.getName());
		}

		Criteria crit2 = session.createCriteria(SchoolClass.class);
		List<SchoolClass> classes = crit2.list();

		for (SchoolClass sClass : classes) {
			System.out.println(sClass.getProfile() + " " + sClass.getStartYear() + " " + sClass.getCurrentYear());
		}

		session.close();
		HibernateUtil.shutdown();

	}

	private void jdbcTest() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end jdbcTest

}
