package a_inheritance;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


//1. MappedSuperclass – the parent classes, can't be entities
//2. Single Table – the entities from different classes with a common ancestor are placed in a single table
//3. Joined Table – each class has its table and querying a subclass entity requires joining the tables
//4. Table-Per-Class – all the properties of a class, are in its table, so no join is required

//========0. MAPPED SUPER CLASSES=======
@MappedSuperclass
class MappedSuperClassEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long generalId;
	private String classType;
	//Getters and setters
	public long getGeneralId() {return generalId;}
	public void setGeneralId(long generalId) {this.generalId = generalId;}
	public String getClassType() {return classType;}
	public void setClassType(String classType) {this.classType = classType;}
	
}

@Entity
class ChildEntity extends MappedSuperClassEntity{
	private String description;
	//Getters and Setters
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
}

//========1. SINGLE_TABLE========
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  //Default strategy, no need to give.
@DiscriminatorColumn(name="product_type", 
					 discriminatorType = DiscriminatorType.STRING)  
													   //Default strategy, no need to give.
@DiscriminatorValue("MyProduct-Test")				   //Default strategy, no need to give.
class MyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    private String name;
    //Getter Setter
	public long getProductId() {return productId;}
	public void setProductId(long productId) {this.productId = productId;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
}

@Entity
class Book extends MyProduct {
    private String author;
    //Getter Setter
	public String getAuthor() {return author;}
	public void setAuthor(String author) {this.author = author;}
}

//========2. JOINED_TABLE========
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalId;
    private String species;
    // constructor, getters, setters 
	public long getAnimalId() {return animalId;}
	public void setAnimalId(long animalId) {this.animalId = animalId;}
	public String getSpecies() {return species;}
	public void setSpecies(String species) {this.species = species;}
}

@Entity
class Pet extends Animal {
    private String name;
    // constructor, getters, setters 
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
}

//========3. TABLE_PER_CLASS========
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class VehicleGeneric {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long vehicleId;
	private String vehicleType;
	//getters and setters
	public long getVehicleId() {return vehicleId;}
	public void setVehicleId(long vehicleId) {this.vehicleId = vehicleId;}
	public String getVehicleType() {return vehicleType;}
	public void setVehicleType(String vehicleType) {this.vehicleType = vehicleType;}
}

@Entity
class ScorpioVehicle extends VehicleGeneric{
	private String description;
	//Getters and Setters
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
}


public class InheritanceMappingTest_4_types {
	public static void inheritanceMapping_1_SingleTable() {
		Configuration cf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(MyProduct.class)
									.addAnnotatedClass(Book.class);
		SessionFactory sf = cf.buildSessionFactory();
		Session ss =  sf.openSession();
		Transaction tx = ss.beginTransaction();
			
			MyProduct mp1 = new MyProduct();
			mp1.setName("GENERIC_PRODUCT");
			ss.save(mp1);
			
			MyProduct mp2 = new MyProduct();
			mp2.setName("GENERIC_PRODUCT");
			ss.save(mp2);
			
			Book bp1 = new Book();
			bp1.setAuthor("Jagadish Raj");
			bp1.setName("BOOK_TYPE_PRODUCT");
			bp1.setProductId(6);
			ss.save(bp1);
			
		tx.commit();
		ss.close();
		sf.close();
	}

	public static void inheritanceMapping_2_JoinedTable() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(Animal.class)
									.addAnnotatedClass(Pet.class)
									.buildSessionFactory();
		Session ss =  sf.getCurrentSession();
		ss.beginTransaction();
			
			Pet p1= new Pet();
			p1.setName("Good Pet");
			p1.setSpecies("Alpha");
			ss.save(p1);
			
		ss.getTransaction().commit();
		sf.close();
	}
	
	public static void inheritanceMapping_3_TablePerClass() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(VehicleGeneric.class)
									.addAnnotatedClass(ScorpioVehicle.class)
									.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();

		VehicleGeneric v1 = new VehicleGeneric();
		v1.setVehicleType("NO_WHEELERS");
		ss.save(v1);
		
		ScorpioVehicle v2 = new ScorpioVehicle();
		v2.setDescription("Confortable and Big car");
		v2.setVehicleType("FOUR_WHEELER");
		ss.save(v2);

		ss.getTransaction().commit();
		sf.close();
	}
	
	public static void inheritanceMapping_mappedSuperClasses_Test() {
		SessionFactory sf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(ChildEntity.class)
									.buildSessionFactory();
		Session ss = sf.getCurrentSession();
		ss.beginTransaction();

		ChildEntity c1= new ChildEntity();
		c1.setClassType("INHERITED");
		c1.setDescription("This class inherited from mapped super classes");
		ss.save(c1);
		
		ss.getTransaction().commit();
		sf.close();
	}
	
	public static void main(String[] args) {
		inheritanceMapping_mappedSuperClasses_Test();
		//inheritanceMapping_1_SingleTable();    //One table contains all data with discriminator
		//inheritanceMapping_2_JoinedTable();    //One table for each sub-class
		//inheritanceMapping_3_TablePerClass();  //One table for each class but with inherited features also
	}
}

/*
Questions:
	1. sessionFactory.getCurrentSession() vs sessionFactory.openSession() 
		//Later creates new session all the time & should be closed.
		//Former can be used if "hibernate.current_session_context_class" = "thread", and not needed to close.
		//Former session is bound to context and not needed to close
Note: Generation Type Auto, Type long has been used everywhere.
    2. GenerationType.AUTO, IDENTITY, SEQUENCE, TABLE, CustomGenerators
	
*/
