package b_association_mapping;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Fetch, Cascade, mappedBy
@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eid;
	private String ename;
	@Temporal(value = TemporalType.DATE)
	private Date dob;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_detail_id")
	private EmployeeDetails employeeDetails;
	
	@OneToMany(mappedBy = "employeeCourseID", cascade = {CascadeType.ALL})
	private List<Course> courses;
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="emp_ment_table",
			joinColumns=@JoinColumn(name="ment_id"),
			inverseJoinColumns=@JoinColumn(name="emp_id")
			)
	private List<Mentor> mentors;
	
	public List<Mentor> getMentors() {
		return mentors;
	}
	public void setMentors(List<Mentor> mentors) {
		this.mentors = mentors;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		return this.eid+" - "+this.ename+" - "+this.employeeDetails+" - "+this.courses;
	}
}
