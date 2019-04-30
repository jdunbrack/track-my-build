package build.trackmy.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="swaps")
public class GearSwap {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int level;
	@ManyToOne(fetch=FetchType.LAZY)
	private Gear oldGear;
	@OneToOne(fetch=FetchType.LAZY)
	private Gear newGear;
	@OneToOne(fetch=FetchType.LAZY)
	private Exile exile;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	public GearSwap(Exile exile, int level, Gear oldGear, Gear newGear) {
		super();
		this.level = level;
		this.oldGear = oldGear;
		this.newGear = newGear;
		this.exile = exile;
	}

	public GearSwap() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Gear getOldGear() {
		return oldGear;
	}

	public void setOldGear(Gear oldGear) {
		this.oldGear = oldGear;
	}

	public Gear getNewGear() {
		return newGear;
	}

	public void setNewGear(Gear newGear) {
		this.newGear = newGear;
	}

	public Exile getExile() {
		return exile;
	}

	public void setExile(Exile exile) {
		this.exile = exile;
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
