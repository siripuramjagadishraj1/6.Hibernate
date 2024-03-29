package b_association_mapping;

import java.util.Arrays;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Z_All_AssociationMappingTest_3_Uni_BiDirectional {
	
	public static void main(String[] args) {
		all_AssociationMapping_Insert_Test();
		//all_AssociationMapping_CRUD_Test();
	}
	
	public static void all_AssociationMapping_CRUD_Test() {
			Configuration cf = new Configuration().configure("hibernate-cfg.xml")
										.addAnnotatedClass(b_association_mapping.Employee.class)
										.addAnnotatedClass(b_association_mapping.EmployeeDetails.class)
										.addAnnotatedClass(b_association_mapping.Course.class)
										.addAnnotatedClass(b_association_mapping.Mentor.class)
										.setProperty("hibernate.hbm2ddl.auto", "update")
					;
			SessionFactory sf = cf.buildSessionFactory();
			Session ss =  sf.openSession();
			Transaction tx = ss.beginTransaction();
				
				//One to one BiDirectional
				Employee emp =ss.get(Employee.class, 1);
				System.out.println(emp);
				
			tx.commit();
			ss.close();
			sf.close();
	}
	
	
	public static void all_AssociationMapping_Insert_Test() {
		Configuration cf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(b_association_mapping.Employee.class)
									.addAnnotatedClass(b_association_mapping.EmployeeDetails.class)
									.addAnnotatedClass(b_association_mapping.Course.class)
									.addAnnotatedClass(b_association_mapping.Mentor.class);
		SessionFactory sf = cf.buildSessionFactory();
		Session ss =  sf.openSession();
		Transaction tx = ss.beginTransaction();
			
			//One to one Unidirectional
			Employee e = new Employee();
			e.setDob(new Date());
			e.setEid(5);
			e.setEname("Jagadish Raj");
				EmployeeDetails ed = new EmployeeDetails();
				ed.setDescription("This is a cool employee");
				
			//one-to-many
				Course c1 = new Course(); 	c1.setCname("Java"); 	c1.setDescription("Java is a good Languge");
				Course c2 = new Course(); 	c2.setCname("Peary"); 	c2.setDescription("Pearl is a good Languge");
				c1.setEmployeeCourseID(e);c2.setEmployeeCourseID(e);
			e.setCourses( Arrays.asList(c1,c2));
			//one-to-one
			e.setEmployeeDetails(ed);
			
			//Many to Many
			Mentor m1= new Mentor();
			m1.setEmployees(Arrays.asList(e));
			e.setMentors(Arrays.asList(m1));
			
			ss.save(e);
			
		tx.commit();
		ss.close();
		sf.close();
	}
}
