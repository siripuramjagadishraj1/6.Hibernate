package a_inheritance;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Entity
class VehicleEntitiy {
	@Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Long id;
 
    @Version
    private Integer version;

	private String vehicleName;
	
	@ElementCollection
	@CollectionTable(
					 joinColumns= @JoinColumn(name="user_id"),
					 name="NickNamesList"
					)
	@Column(name="nickname")
	@OrderColumn(name = "index_id")
	private List<String> nickNamesList;
	
	@ElementCollection
	@CollectionTable(
					 joinColumns= @JoinColumn(name="user_id"), 
					 name="NickNamesSet"
					)
	@Column(name="nickname")
	private Set<String> nickNamesSet;
	
	@ElementCollection
	@CollectionTable(
					 joinColumns= @JoinColumn(name="user_id"), 
					 name="mappedNicknames"
					)
	@Column(name="nickname")
	private Map<String, String> mappedNicknames;
	
	public Map<String, String> getMappedNicknames() {
		return mappedNicknames;
	}
	public void setMappedNicknames(Map<String, String> mappedNicknames) {
		this.mappedNicknames = mappedNicknames;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public List<String> getNickNamesList() {
		return nickNamesList;
	}
	public void setNickNamesList(List<String> nickNamesList) {
		this.nickNamesList = nickNamesList;
	}
	public Set<String> getNickNamesSet() {
		return nickNamesSet;
	}
	public void setNickNamesSet(Set<String> nickNamesSet) {
		this.nickNamesSet = nickNamesSet;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}

public class CollectionMappingTest_List_Set_Map {
	
	public static void main(String[] args) {
		collectionMapping();
	}
	
	public static void collectionMapping() {
		Configuration cf = new Configuration().configure("hibernate-cfg.xml")
									.addAnnotatedClass(VehicleEntitiy.class);
		SessionFactory sf = cf.buildSessionFactory();
		Session ss =  sf.openSession();
		Transaction tx = ss.beginTransaction();
			
			//One to one Unidirectional
		VehicleEntitiy ve = new VehicleEntitiy();
		ve.setNickNamesList(Arrays.asList("Sony","Ramya","Sony"));
		ve.setNickNamesSet(new HashSet<String>(Arrays.asList("Sony","Ramya","Sony")));
		ve.setVehicleName("JEEP");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("1", "One");
			map.put("2", "Two");
			map.put("3", "Three");
		ve.setMappedNicknames(map);
		ss.save(ve);
			
			
		tx.commit();
		ss.close();
		sf.close();
	}
}
