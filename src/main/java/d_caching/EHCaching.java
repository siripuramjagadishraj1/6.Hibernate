package d_caching;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Entity
class BookEntityCache {
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

public class EHCaching {
	
	public static void main(String[] args) {
		saveData();
		getData();
	}
	
	
	public static void saveData() {
		/*
		Configuration configuration = new Configuration();
    	configuration.configure("hibernate-cfg-cache-config.xml").addAnnotatedClass(BookEntityCache.class).setProperty("hibernate.hbm2ddl.auto", "create");
    	System.out.println("Hibernate Configuration loaded");
    	
    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
    										applySettings(configuration.getProperties()).build();
    	System.out.println("Hibernate serviceRegistry created");
    	
    	SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
    	
    	Session ss = sf.getCurrentSession();
    	ss.beginTransaction();
    	*/
		SessionFactory sf = new Configuration().configure("hibernate-cfg-cache-config.xml")
				.addAnnotatedClass(BookEntityCache.class)
				.setProperty("hibernate.hbm2ddl.auto", "create")
				.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
		
			//1. Method 1 [NORMAL]
			BookEntityCache be = new BookEntityCache();
			be.setBname("Tata");
			ss.save(be);
			
			BookEntityCache be2 = new BookEntityCache();
			be2.setBname("Ratan");
			ss.save(be2);
			
		ss.getTransaction().commit();
		sf.close();
	}
	
	public static void getData() {
		/*
		Configuration configuration = new Configuration();
    	configuration.configure("hibernate-cfg-cache-config.xml").addAnnotatedClass(BookEntityCache.class).setProperty("hibernate.hbm2ddl.auto", "update");
    	System.out.println("Hibernate Configuration loaded");
    	
    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
    										applySettings(configuration.getProperties()).build();
    	System.out.println("Hibernate serviceRegistry created");
    	
    	SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
    	
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
		*/
		SessionFactory sf = new Configuration().configure("hibernate-cfg-cache-config.xml")
				.addAnnotatedClass(BookEntityCache.class)
				.setProperty("hibernate.hbm2ddl.auto", "update")
				.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
		
			//1. Method 1 [NORMAL]
		BookEntityCache entity1 = (BookEntityCache) ss.get(BookEntityCache.class, 1l);
		BookEntityCache entity2 = (BookEntityCache) ss.load(BookEntityCache.class, 2l);
		
		System.out.println(entity1);
		System.out.println(entity2);
			
			
		ss.getTransaction().commit();
		sf.close();
	}
}
