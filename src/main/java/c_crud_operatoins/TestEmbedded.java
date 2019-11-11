package c_crud_operatoins;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@Entity
@Table(name = "STUDENT")
class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STUDENT_ID")
	private long studentId;
	private String studentName;
	private Address studentAddress;
	//Getter and setters
	public Student(String studentName, Address studentAddress) {
		super();
		this.studentName = studentName;
		this.studentAddress = studentAddress;
	}
	public Student() {super();}
	public long getStudentId() {return studentId;}
	public void setStudentId(long studentId) {this.studentId = studentId;}
	public String getStudentName() {return studentName;}
	public void setStudentName(String studentName) {this.studentName = studentName;}
	public Address getStudentAddress() {return studentAddress;}
	public void setStudentAddress(Address studentAddress) {this.studentAddress = studentAddress;}
}

@Embeddable
class Address {
	private String street;
	private String city;
	private String state;
	private String zipcode;
	//Getters and setters
	public String getStreet() {return street;}
	
	public Address() {super();}
	public Address(String street, String city, String state, String zipcode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	public void setStreet(String street) {this.street = street;}
	public String getCity() {return city;}
	public void setCity(String city) {this.city = city;}
	public String getState() {return state;}
	public void setState(String state) {this.state = state;}
	public String getZipcode() {return zipcode;}
	public void setZipcode(String zipcode) {this.zipcode = zipcode;}
	
}


public class TestEmbedded {
	public static void main(String[] args) throws Exception {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
										.addAnnotatedClass(Address.class)
										.addAnnotatedClass(Student.class)
										.setProperty("hibernate.hbm2ddl.auto", "create").buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
		
			Address address = new Address("OMR Road", "Chennai", "TN", "600097");
			Student student = new Student("Eswar", address);
			ss.save(student);

		ss.getTransaction().commit();
		sf.close();

	}
}

//NOTE: Embedded is used to save one entity in database.
