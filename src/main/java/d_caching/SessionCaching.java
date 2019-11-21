package d_caching;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bid;
	private String bname;
	@Version
	private Integer versionId;
	//Getters and setters
	public long getBid() {return bid;}
	public void setBid(long bid) {this.bid = bid;}
	public String getBname() {return bname;}
	public void setBname(String bname) {this.bname = bname;}
	@Override
	public String toString() {return "[bid:"+this.bid+", bname:"+this.bname+"]";}
}


public class SessionCaching {
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
			BookEntity entity1 = (BookEntity) ss.get(BookEntity.class, 1l);
			BookEntity entity2 = (BookEntity) ss.load(BookEntity.class, 2l);
			
			System.out.println(ss.get(BookEntity.class, 1l));
			System.out.println(ss.get(BookEntity.class, 1l));
			System.out.println(ss.get(BookEntity.class, 1l));
			System.out.println("Example showing Session Cache: "+ss.contains(entity1));
			ss.evict(entity1);
			System.out.println("====SESSION EVICTED====");
			System.out.println("Example showing Session Cache: "+ss.contains(entity1));
			
			
			System.out.println(entity2);
			ss.clear();
			System.out.println("====SESSION CLEARED====");
			entity1 = (BookEntity) ss.load(BookEntity.class, 1l);
			entity1.setBname("Mufti Ahmed");
			entity1.setBname("Mufti Ahmed Three");
			entity2 = (BookEntity) ss.load(BookEntity.class, 2l);
			System.out.println(entity1);
			System.out.println(entity2);
			
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
