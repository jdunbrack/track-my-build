package build.trackmy.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="exiles")
public class Exile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String baseClass;
	private String ascendancy;
	private int level;
	@OneToMany(mappedBy="exile", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<GearSwap> gearSwaps;
	@OneToMany(mappedBy="exile", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@MapKey(name="gearSlot")
	@Column(nullable=true)
	private Map<String, Gear> items;
	@OneToMany(mappedBy="exile", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@MapKey(name="level")
	@Column(nullable=true)
	private Map<Integer,PassiveTree> passiveTrees;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	public Exile(String name, int level, String baseClass, String ascendency) {
		super();
		this.name = name;
		this.level = level;
		this.baseClass = baseClass;
		this.ascendancy = ascendency;
	}

	public Exile() {
		super();
	}

	public String toString() {
		String output = "";
		output += this.getName() + "\n";
		output += this.getLevel() + "\n";
		output += this.getBaseClass() + "\n";
		output += this.getAscendancy() + "\n";
		return output;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseClass() {
		return baseClass;
	}

	public void setBaseClass(String baseClass) {
		this.baseClass = baseClass;
	}

	public String getAscendancy() {
		return ascendancy;
	}

	public void setAscendancy(String ascendancy) {
		this.ascendancy = ascendancy;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Map<String, Gear> getItems() {
		return items;
	}

	public void setItems(Map<String, Gear> items) {
		this.items = items;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Map<Integer,PassiveTree> getPassiveTrees() {
		return passiveTrees;
	}

	public void setPassiveTrees(Map<Integer,PassiveTree> passiveTrees) {
		this.passiveTrees = passiveTrees;
	}

	public List<GearSwap> getGearSwaps() {
		return gearSwaps;
	}

	public void setGearSwaps(List<GearSwap> gearSwaps) {
		this.gearSwaps = gearSwaps;
	}
}
