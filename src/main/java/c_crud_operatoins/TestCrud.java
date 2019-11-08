package c_crud_operatoins;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;

import com.sun.istack.NotNull;


@Entity
class Router{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer rid;
	@NotNull
	private String routerName;
	private String isGood;
	//Getter and Setter
	public Integer getRid() {return rid;}
	public void setRid(Integer rid) {this.rid = rid;}
	public String getRouterName() {return routerName;}
	public void setRouterName(String routerName) {this.routerName = routerName;}
	public String getIsGood() {return isGood;}
	public void setIsGood(String isGood) {this.isGood = isGood;}
	@Override
	public String toString() {return this.rid+" - "+ this.routerName+" - "+this.isGood;}
	
}

public class TestCrud {
	public static void main(String[] args) {
		//CREATED by Hibernate
		//UPDATE/INSERT
		insertUpdateData();
		//RETRIVE
		getInsertedData();
		//DELETE
		//delteInsertedData();
	}

	public static void getInsertedData() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(Router.class)
									.setProperty("hibernate.hbm2ddl.auto", "update")
									.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();
			//1. Method 1 [NORMAL]
			System.out.println("\n====1. Method 1 [NORMAL]====");
			Router ro =(Router)ss.get(Router.class, 1);
			ss.delete(ro);
			System.out.println(ro);
			ro.setIsGood("VERY GOOD");
			
			//2. Method 2 [HQL]
			System.out.println("\n====2. Method 2 [HQL ss.createQuery]====");
			String hqlQuery = "from Router r where r.rid = 2";
			Query<Router> query = ss.createQuery(hqlQuery);
			List<Router> routerList = query.list();
			System.out.println(routerList);
			
			//3. Method 3 [SQL]
			System.out.println("\n====3. Method 3 [SQL ss.createSQLQuery]====");
			SQLQuery<Router> queryRouter = (SQLQuery<Router>)ss.createSQLQuery("select r.rid, r.isgood, r.routername from Router r where r.rid =2");
		    List<Router> routerList2 = (List<Router>)queryRouter.list();
		    System.out.println(routerList2.get(0));
		    
		    //4. Method 4 [CRITERIA]
		    System.out.println("\n====4. Method 4 [CRITERIA ss.createCriteria]====");
		    Criteria criteria = ss.createCriteria(Router.class);
			criteria.add(Restrictions.gt("rid",1));
		    List employeeList = criteria.list();
		    System.out.println(employeeList);
			
		
		ss.getTransaction().commit();
		sf.close();
	}
	
	public static void insertUpdateData() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(Router.class)
									.setProperty("hibernate.hbm2ddl.auto", "create")
									.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();

			Router r1 = new Router();
			r1.setIsGood("Good");r1.setRouterName("BSNL");
		
			Router r2 = new Router();
			r2.setIsGood("BAD");r2.setRouterName("AIR-TEL");
			
			List<Router> routerList = Arrays.asList(r1,r2);
			routerList.forEach( 
							(e) -> ss.save(e) 
						);

		ss.getTransaction().commit();
		sf.close();
	}
}
