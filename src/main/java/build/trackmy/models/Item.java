package build.trackmy.models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private ArrayList<String> itemName;
	private ArrayList<String> statBlock;
	private String requirements;
	private ArrayList<String> implicitMods;
	@Lob
	private ArrayList<String> explicitMods;
	@Lob
	private ArrayList<String> craftedMods;
	private String image;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;

	
	public Item() {
		
	}
	
	public Item(ArrayList<String> itemName, String itemCategory, ArrayList<String> statBlock,
			ArrayList<String> implicitMods, ArrayList<String> explicitMods, String image) {
		super();
		this.itemName = itemName;
		this.statBlock = statBlock;
		this.implicitMods = implicitMods;
		this.explicitMods = explicitMods;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<String> getItemName() {
		return itemName;
	}

	public void setItemName(ArrayList<String> itemName) {
		this.itemName = itemName;
	}

	public ArrayList<String> getStatBlock() {
		return statBlock;
	}

	public void setStatBlock(ArrayList<String> statBlock) {
		this.statBlock = statBlock;
	}

	public ArrayList<String> getImplicitMods() {
		return implicitMods;
	}

	public void setImplicitMods(ArrayList<String> implicitMods) {
		this.implicitMods = implicitMods;
	}

	public ArrayList<String> getExplicitMods() {
		return explicitMods;
	}

	public void setExplicitMods(ArrayList<String> explicitMods) {
		this.explicitMods = explicitMods;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public ArrayList<String> getCraftedMods() {
		return craftedMods;
	}

	public void setCraftedMods(ArrayList<String> craftedMods) {
		this.craftedMods = craftedMods;
	}
	
}
