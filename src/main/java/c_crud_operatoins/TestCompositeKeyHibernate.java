package c_crud_operatoins;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@Embeddable
class EmployeeIdentity implements Serializable {
    private String employeeId;
    private String companyId;
    public EmployeeIdentity() {}
    public EmployeeIdentity(String employeeId, String companyId) {
        this.employeeId = employeeId;
        this.companyId = companyId;
    }
    public String getEmployeeId() {return employeeId;}
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId;}
    public String getCompanyId() {return companyId;}
    public void setCompanyId(String companyId) {this.companyId = companyId;}

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeIdentity that = (EmployeeIdentity) o;

        if (!employeeId.equals(that.employeeId)) return false;
        return companyId.equals(that.companyId);
    }

    @Override
    public int hashCode() {
        int result = employeeId.hashCode();
        result = 31 * result + companyId.hashCode();
        return result;
    }
    */
    
}

@Entity
class Employee {
    @EmbeddedId
    private EmployeeIdentity employeeIdentity;
    private String name;
    private String email;

    @Column(name = "phone_number", unique = false, nullable = false)
    private String phoneNumber;

    public Employee() {}

    public Employee(EmployeeIdentity employeeIdentity, String name, String email, String phoneNumber) {
        this.employeeIdentity = employeeIdentity;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

	public EmployeeIdentity getEmployeeIdentity() {
		return employeeIdentity;
	}

	public void setEmployeeIdentity(EmployeeIdentity employeeIdentity) {
		this.employeeIdentity = employeeIdentity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

public class TestCompositeKeyHibernate {
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
												.addAnnotatedClass(Employee.class)
												.addAnnotatedClass(EmployeeIdentity.class)
												.setProperty("hibernate.hbm2ddl.auto", "update")
												.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();

		Employee employee = new Employee(new EmployeeIdentity("E-102", "D-458"),
                "Rajeev Kumar Singh",
                "rajeev@callicoder.com",
                "+91-9999999999");
		ss.save(employee);

		ss.getTransaction().commit();
		sf.close();

	}
}
