package d_caching;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bid;
	private String bname;
	//Getters and setters
	public long getBid() {return bid;}
	public void setBid(long bid) {this.bid = bid;}
	public String getBname() {return bname;}
	public void setBname(String bname) {this.bname = bname;}
}


public class TestCaching {
	public static void main(String[] args) {
		saveData();
		getData();
	}
	
	public static void getData() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(BookEntity.class)
									.setProperty("hibernate.hbm2ddl.auto", "update")
									.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
		
			//1. Method 1 [NORMAL]
			System.out.println(ss.get(BookEntity.class, 1l));
			System.out.println(ss.get(BookEntity.class, 1l));
			System.out.println(ss.get(BookEntity.class, 1l));
			
		ss.getTransaction().commit();
		sf.close();
	}

	public static void saveData() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(BookEntity.class)
									.setProperty("hibernate.hbm2ddl.auto", "create")
									.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
		
			//1. Method 1 [NORMAL]
			BookEntity be = new BookEntity();
			be.setBname("Tata");
			ss.save(be);
			
			BookEntity be2 = new BookEntity();
			be2.setBname("Ratan");
			ss.save(be2);
			
		ss.getTransaction().commit();
		sf.close();
	}
}
