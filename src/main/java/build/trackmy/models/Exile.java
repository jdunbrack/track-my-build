package build.trackmy.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private int baseClass;
	private int ascendency;
	private int level;
	@OneToMany(mappedBy="exile", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Gear> items;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	public Exile(String name, int level, int baseClass, int ascendency) {
		super();
		this.name = name;
		this.level = level;
		this.baseClass = baseClass;
		this.ascendency = ascendency;
	}

	public Exile() {
		super();
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

	public int getBaseClass() {
		return baseClass;
	}

	public void setBaseClass(int baseClass) {
		this.baseClass = baseClass;
	}

	public int getAscendency() {
		return ascendency;
	}

	public void setAscendency(int ascendency) {
		this.ascendency = ascendency;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Gear> getItems() {
		return items;
	}

	public void setItems(List<Gear> items) {
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
}
