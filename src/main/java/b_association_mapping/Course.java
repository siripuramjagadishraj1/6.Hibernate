package b_association_mapping;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;
	private String cname;
	private String description;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "employee_course_ids")
	private Employee employeeCourseID; 
	
	public Integer getCid() {
		return cid;
	}
	public Employee getEmployeeCourseID() {
		return employeeCourseID;
	}
	public void setEmployeeCourseID(Employee employeeCourseID) {
		this.employeeCourseID = employeeCourseID;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return this.cid+" - "+this.cname+" - "+this.description;
	}
}
