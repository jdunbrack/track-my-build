package build.trackmy.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class Gear extends Item {
	private int itemLevel;
	private ArrayList<String> sockets;
	private int rarity;
	private String gearSlot;
	@OneToMany(mappedBy="gear", cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
	private List<Gem> gems;
	@OneToOne(mappedBy="oldGear", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private GearSwap swappedFrom;
	@OneToOne(mappedBy="newGear", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private GearSwap swappedTo;
	@ManyToOne(fetch=FetchType.LAZY)
	private Exile exile;
	
	public Gear(ArrayList<String> sockets, char rarity) {
		super();
		this.sockets = sockets;
		this.rarity = rarity;
	}

	public Gear() {
		super();
	}

	public ArrayList<String> getSockets() {
		return sockets;
	}

	public void setSockets(ArrayList<String> sockets) {
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
	
	public String getDataDump() {
		String output = "";

		for (String line: this.getItemName()) {
			output += line + "\n";
		}
		output += "\n\n";
		output += "Item level: " + this.getItemLevel() + "\n";
		if (this.getStatBlock() != null) {
			for (String line: this.getStatBlock()) {
				output += line + "\n";
			}
		}

		output += ("\n\n");
		if (this.getImplicitMods() != null) {
			for (String line: this.getImplicitMods()) {
				output += line + "\n";
			}
			output += "\n\n";
		}

		if (this.getExplicitMods() != null) {
			for (String line: this.getExplicitMods()) {
				output += line + "\n";
			}
		}
		
		if (this.getCraftedMods() != null) {
			for (String line: this.getCraftedMods()) {
				output += line + "\n";
			}
		}
		
		if (this.getSockets() != null) {
			output += this.getSockets().toString();
		}
		return output;
	}
	
	public String getHtmlData() {
		String output = "";
		if (this.rarity == 0) {
			output += "<div class='normal-header'>";
			output += "<img class='item-header1' src='/img/bg/bg-normal-left.png'>";
			output += "<span class='normal'>";
			
			output += this.getItemName().get(0);
			
			output += "</span>";
			output += "<img class='item-header2' src='/img/bg/bg-normal-right.png'>";
			output += "</div>";
			
			output += "Item level: " + this.getItemLevel() + "<br/>";
			
			if (this.getStatBlock() != null) {
				for (String line: this.getStatBlock()) {
					output += line + "<br/>";
				}
			}
			
			if (this.getRequirements() != null) {
				output += "<img class='break' src='/img/bg/normal-br.png'>";
				output += this.getRequirements() + "<br/>";
			}
			
			output += "<img class='break' src='/img/bg/normal-br.png'>";
			
			if (this.getImplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getImplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
				output += "<img class='break' src='/img/bg/normal-br.png'>";
			}

			if (this.getExplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getExplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
			}
			
		} else if (this.rarity == 1) {
			output += "<div class='magic-header'>";
			output += "<img class='item-header1' src='/img/bg/bg-magic-left.png'>";
			output += "<span class='magic'>";
			for (String line: this.getItemName()) {
				output += line + "<br/>";
			}
			output += "</span>";
			output += "<img class='item-header2' src='/img/bg/bg-magic-right.png'>";
			output += "</div>";
			
			output += "Item level: " + this.getItemLevel() + "<br/>";
			
			if (this.getStatBlock() != null) {
				for (String line: this.getStatBlock()) {
					output += line + "<br/>";
				}
			}
			
			if (this.getRequirements() != null) {
				output += "<img class='break' src='/img/bg/magic-br.png'>";
				output += this.getRequirements() + "<br/>";
			}
			
			output += "<img class='break' src='/img/bg/magic-br.png'>";
			
			if (this.getImplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getImplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
				output += "<img class='break' src='/img/bg/magic-br.png'>";
			}

			if (this.getExplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getExplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
			}
			
			if (this.getCraftedMods() != null) {
				output += "<span class='crafted'>";
				for (String line: this.getCraftedMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
			}

		} else if (this.rarity == 2) {
			output += "<div class='rare-header'>";
			output += "<img class='item-header1' src='/img/bg/bg-rare-left.png'>";
			output += "<span class='rare'>";
			for (String line: this.getItemName()) {
				output += line + "<br/>";
			}
			output += "</span>";
			output += "<img class='item-header2' src='/img/bg/bg-rare-right.png'>";
			output += "</div>";
			
			output += "Item level: " + this.getItemLevel() + "<br/>";
			
			if (this.getStatBlock() != null) {
				for (String line: this.getStatBlock()) {
					output += line + "<br/>";
				}
			}
			
			if (this.getRequirements() != null) {
				output += "<img class='break' src='/img/bg/rare-br.png'>";
				output += this.getRequirements() + "<br/>";
			}
			
			output += "<img class='break' src='/img/bg/rare-br.png'>";
			
			if (this.getImplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getImplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
				output += "<img class='break' src='/img/bg/rare-br.png'>";
			}

			if (this.getExplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getExplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
			}
			
			if (this.getCraftedMods() != null) {
				output += "<span class='crafted'>";
				for (String line: this.getCraftedMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
			}
			
		} else if (this.rarity == 3) {
			output += "<div class='unique-header'>";
			output += "<img class='item-header1' src='/img/bg/bg-unique-left.png'>";
			output += "<span class='unique'>";
			for (String line: this.getItemName()) {
				output += line + "<br/>";
			}
			output += "</span>";
			output += "<img class='item-header2' src='/img/bg/bg-unique-right.png'>";
			output += "</div>";
			
			output += "Item level: " + this.getItemLevel() + "<br/>";
			
			if (this.getStatBlock() != null) {
				for (String line: this.getStatBlock()) {
					output += line + "<br/>";
				}
			}
			
			if (this.getRequirements() != null) {
				output += "<img class='break' src='/img/bg/unique-br.png'>";
				output += this.getRequirements() + "<br/>";
			}
			
			output += "<img class='break' src='/img/bg/unique-br.png'>";
			
			if (this.getImplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getImplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
				output += "<img class='break' src='/img/bg/unique-br.png'>";
			}

			if (this.getExplicitMods() != null) {
				output += "<span class='mods'>";
				for (String line: this.getExplicitMods()) {
					output += line + "<br/>";
				}
				output += "</span>";
			}
			
		}
		
		System.out.println(this.getSockets());
		
		if (this.getSockets() != null && this.getSockets().size() > 0) {
			output += "<img class='break' src='/img/bg/normal-br.png'>";
			output += "<span class='text-center'>Sockets: " + this.getSockets() + "</span>";
			output += "<img class='break' src='/img/bg/normal-br.png'>";
//			output += "<span>Socketed Gems, grouped by links:</span>";
//			for (String link: this.getSockets()) {
//				output += "<div class='socket-group'>";
//				int count = 0;
//				for (char socket: link.toCharArray()) {
//					output += "<span class='socket-gem " + socket + "'>";
//					output += this.getGems().get(count).toSocketLine();
//					output += "</span>";
//					count++;
//				}
//				output += "</div>";
//			}
		}

		return output;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public Exile getExile() {
		return exile;
	}

	public void setExile(Exile exile) {
		this.exile = exile;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public String getGearSlot() {
		return gearSlot;
	}

	public void setGearSlot(String gearSlot) {
		this.gearSlot = gearSlot;
	}

	public GearSwap getSwapTo() {
		return swappedTo;
	}

	public void setSwapTo(GearSwap swapTo) {
		this.swappedTo = swapTo;
	}

	public GearSwap getSwappedFrom() {
		return swappedFrom;
	}

	public void setSwappedFrom(GearSwap swappedFrom) {
		this.swappedFrom = swappedFrom;
	}
	
	
	
}
