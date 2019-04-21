package build.trackmy.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="gems")
public class Gem extends Item {
	private int level;
	private char color;
	private String types;
	private int quality;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gear_id")
	private Gear gear;
	
	public Gem(int level, char color, String types, int quality, Gear gear) {
		super();
		this.level = level;
		this.color = color;
		this.types = types;
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

	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
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
	
	
	
	// this.name = typeLine;
}
