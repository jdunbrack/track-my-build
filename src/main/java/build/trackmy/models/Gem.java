package build.trackmy.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="gems")
public class Gem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean isSupport;
	private int level;
	private int quality;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gear_id")
	private Gear gear;
	
	public Gem(int level, char color, String types, int quality, Gear gear) {
		super();
		this.level = level;
		this.quality = quality;
		this.gear = gear;
	}

	public Gem() {
		// TODO Auto-generated constructor stub
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public Gear getGear() {
		return gear;
	}

	public void setGear(Gear gear) {
		this.gear = gear;
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

	public boolean isSupport() {
		return isSupport;
	}

	public void setSupport(boolean isSupport) {
		this.isSupport = isSupport;
	}
	
	public String toSocketLine() {
		return this.name + " " + this.level + "/" + this.quality; 
	}
	
	
	
	// this.name = typeLine;
}
