package build.trackmy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.models.Gem;
import build.trackmy.repositories.GemRepository;
import build.trackmy.services.GemService;


public class JsonParser {
	public static ArrayList<ArrayList<String>> ascendancyDict;
	private final String name;
	private final String POESESSID;
	private final ObjectMapperToMap obj = new ObjectMapperToMap();
	private LinkedHashMap<String,Object> json;
	
	public JsonParser(String name, String POESESSID) {
		this.name = name;
		this.POESESSID = POESESSID;
		ascendancyDict = new ArrayList<ArrayList<String>>();
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Scion", "Ascendant")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Marauder", "Juggernaut", "Berserker", "Chieftan")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Ranger", "Raider", "Deadeye", "Pathfinder")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Witch", "Occultist", "Elementalist", "Necromancer")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Duelist", "Slayer", "Gladiator", "Champion")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Templar", "Inquisitor", "Heirophant", "Guardian")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Shadow", "Assassin", "Trickster", "Saboteur")));
	}
	

	
	public void pullJson() {
		try {
			this.json = obj.readJsonToMap("/get-items?character=" + name, POESESSID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Exile parseAll() {
		if (this.json == null) {
			this.pullJson();
		}
		
		ArrayList<LinkedHashMap<String, Object>> itemList = (ArrayList<LinkedHashMap<String, Object>>) json.get("items");
		LinkedHashMap<String, Object> characterInfo = (LinkedHashMap<String, Object>) json.get("character"); 
		
		Exile newExile = parseExile(characterInfo);
		
		HashMap<String, Gear> items = new HashMap<String, Gear>();
		for (LinkedHashMap<String, Object> item : itemList) {
			if (item.get("inventoryId").toString().equals("MainInventory")) {
				continue;	
			}
			Gear itemEntity = parseGear(item);
			items.put(itemEntity.getGearSlot(), itemEntity);
		}
		
		newExile.setItems(items);
		
		return newExile;
	}

	public Exile parseExile(LinkedHashMap<String, Object> json) {
		Exile thisExile = new Exile();
		thisExile.setName(json.get("name").toString());
		
		thisExile.setBaseClass(ascendancyDict.get(Integer.parseInt(json.get("classId").toString())).get(0));
		if (Integer.parseInt(json.get("ascendancyClass").toString()) == 0) {
			thisExile.setAscendancy("None");
		} else { 
			thisExile.setAscendancy(ascendancyDict.get(Integer.parseInt(json.get("classId").toString())).get(Integer.parseInt(json.get("ascendancyClass").toString())));
		}
		thisExile.setLevel(Integer.parseInt(json.get("level").toString()));
		return thisExile;
	}
	
	public Gear parseGear(LinkedHashMap<String, Object> gearJson) {
		Gear gear = new Gear();
		ArrayList<String> tempList = new ArrayList<String>();
		
		// add image and rarity;
		gear.setImage((String) gearJson.get("icon"));
		gear.setRarity((Integer) gearJson.get("frameType"));
		
		// parse the header (name for rare/unique, base type for all)
		if (gearJson.get("name").toString().length() > 0) {
			tempList.add((String) gearJson.get("name"));
		}
		tempList.add((String) gearJson.get("typeLine"));
		gear.setItemName((ArrayList<String>) tempList.clone());
		tempList.clear();
		
		// parse the statblock from ilvl (inclusive) through requirements (exclusive)
		gear.setItemLevel(Integer.parseInt(gearJson.get("ilvl").toString()));
		
		if (gearJson.get("properties") != null) {
			gear.setStatBlock(parseBlock((ArrayList<LinkedHashMap<String, Object>>) gearJson.get("properties")));
		}
		
		// parse requirements
		if (gearJson.get("requirements") != null) {
			gear.setRequirements(parseReqs((ArrayList<LinkedHashMap<String, Object>>) gearJson.get("requirements")));
		}
		
		// parse implicit modifiers
		if (gearJson.get("implicitMods") != null) {
			gear.setImplicitMods((ArrayList<String>) gearJson.get("implicitMods"));
		}
		
		// parse explicit modifiers
		if (gearJson.get("explicitMods") != null) {
			gear.setExplicitMods((ArrayList<String>) gearJson.get("explicitMods"));
		}
		
		// parse crafted modifiers
		if (gearJson.get("craftedMods") != null) {
			gear.setCraftedMods((ArrayList<String>) gearJson.get("craftedMods"));
		}
		
		// parse sockets, grouping linked sockets together
		if (gearJson.get("sockets") != null) {
			ArrayList<String> sockets = new ArrayList<String>(Arrays.asList("","","","","",""));
			List<LinkedHashMap<String, Object>> socketList = (List<LinkedHashMap<String, Object>>) gearJson.get("sockets");
			for (LinkedHashMap<String,Object> socket: socketList) {
				if (sockets.get((Integer) socket.get("group")).length() > 0) {
					String socketString = sockets.get((Integer) socket.get("group"));
					socketString += (String) socket.get("sColour");
					sockets.set((Integer) socket.get("group"), socketString);
				} else {
					sockets.set((Integer) socket.get("group"), (String) socket.get("sColour"));
				}
			}
			while (sockets.contains("")) {
				sockets.remove(sockets.indexOf(""));
			}
			gear.setSockets(sockets);
		}
				
		// parse gear slot
		String gearSlot = gearJson.get("inventoryId").toString();
		if (gearSlot.equals("Flask")) {
			gearSlot += gearJson.get("x").toString();
		}
		gear.setGearSlot(gearSlot);
		
		return gear;
	}
	
	public LinkedHashMap<String,ArrayList<Gem>> parseGems() {
		LinkedHashMap<String, ArrayList<Gem>> gemMap = new LinkedHashMap<String, ArrayList<Gem>>();
		ArrayList<Gem> gemList = new ArrayList<Gem>();
		ArrayList<LinkedHashMap<String, Object>> itemList = (ArrayList<LinkedHashMap<String, Object>>) json.get("items"); 
		for (LinkedHashMap<String, Object> item : itemList) {
			ArrayList<LinkedHashMap<String,Object>> gemsList = (ArrayList<LinkedHashMap<String,Object>>) item.get("socketedItems");
			if (gemList == null || gemList.size() == 0) {
				continue;
			}
			for (LinkedHashMap<String,Object> gemData: gemsList) {
				if (gemData.get("abyssJewel") == null) {
					Gem newGem = new Gem();
					newGem.setName((String) gemData.get("typeLine"));
					ArrayList<LinkedHashMap<String,Object>> gemProperties = (ArrayList<LinkedHashMap<String,Object>>) gemData.get("properties");
					ArrayList<Object> gemValues = (ArrayList<Object>) gemProperties.get(1).get("values");
					gemValues = (ArrayList<Object>) gemValues.get(0);
					String gemLevel = (String) gemValues.get(0);
					gemLevel = gemLevel.replace(" (Max)", "");
					newGem.setLevel(Integer.parseInt(gemLevel));
					if (gemProperties.get(gemProperties.size() - 1).equals("Quality")) {
						gemValues = (ArrayList<Object>) gemProperties.get(gemProperties.size() - 1).get("values");
						gemValues = (ArrayList<Object>) gemValues.get(0);
						String quality = (String) gemValues.get(0);
						newGem.setQuality(Integer.parseInt(quality.substring(1, quality.length() - 2)));
					} else {
						newGem.setQuality(0);
					}
					gemList.add(newGem);
				}
			}
			gemMap.put(item.get("inventoryId").toString(), gemList);
		}
		return gemMap;
	}
	
	public ArrayList<String> parseBlock(ArrayList<LinkedHashMap<String, Object>> propertyList) {
		String line = new String();
		ArrayList<String> statBlock = new ArrayList<String>();
		
		for (int i = 0; i < propertyList.size(); i++) {

			LinkedHashMap<String,Object> propertyMap = (LinkedHashMap<String, Object>) propertyList.get(i);
			ArrayList<Object> tempList = (ArrayList<Object>) propertyMap.get("values");
			if (tempList.size() < 1) {
				continue;
			}
			
			if (propertyMap.get("name").toString().contains("%0")) {
				line = propertyMap.get("name").toString();
				
				line = line.replace("%0", ((ArrayList<Object>) tempList.get(0)).get(0).toString());		
				if (line.contains("%1")) {
					line = line.replace("%1", ((ArrayList<Object>) tempList.get(1)).get(0).toString());
				}
			} else {
				tempList = (ArrayList<Object>) tempList.get(0);
				line = (String) propertyMap.get("name") + ": ";
				line += (String) tempList.get(0);
			}
			statBlock.add(line);
		}
		
		return statBlock;
	}
	
	public String parseReqs(ArrayList<LinkedHashMap<String, Object>> reqsList) {
		String reqsLine = "Requires ";
		
		for (int i = 0; i < reqsList.size(); i++) {
			LinkedHashMap<String,Object> reqsMap = (LinkedHashMap<String, Object>) reqsList.get(i);
			ArrayList<Object> tempList = (ArrayList<Object>) reqsMap.get("values");
			if (tempList.size() < 1) {
				continue;
			}
			tempList = (ArrayList<Object>) tempList.get(0);
			reqsLine += (String) reqsMap.get("name") + " " + (String) tempList.get(0);
			if (i != reqsList.size() - 1) {
				reqsLine += ", ";
			}
		}
		
		return reqsLine;
		
	}
	
}









