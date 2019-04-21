package build.trackmy.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class Gear extends Item {
	private String[] sockets;
	private int rarity;
	@OneToMany(mappedBy="gear", cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
	private List<Gem> gems;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gear_id")
	private Exile exile;
	
	public Gear(String[] sockets, char rarity) {
		super();
		this.sockets = sockets;
		this.rarity = rarity;
	}

	public Gear() {
		super();
	}

	public String[] getSockets() {
		return sockets;
	}

	public void setSockets(String[] sockets) {
		this.sockets = sockets;
	}

	public Integer getRarity() {
		return rarity;
	}

	public void setRarity(Integer integer) {
		this.rarity = integer;
	}

	public List<Gem> getGems() {
		return gems;
	}

	public void setGems(List<Gem> gems) {
		this.gems = gems;
	}
	
	
	
	
}
