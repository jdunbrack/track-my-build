package build.trackmy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.models.Gem;

public class ObjectMapperToMapTest {
	public static ArrayList<ArrayList<String>> ascendencyDict;
	
	
	public static void main(String[] args)  {
		ascendencyDict = new ArrayList<ArrayList<String>>();
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Scion", "Ascendant")));
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Marauder", "Juggernaut", "Berserker", "Chieftan")));
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Ranger", "Raider", "Deadeye", "Pathfinder")));
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Witch", "Occultist", "Elementalist", "Necromancer")));
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Duelist", "Slayer", "Gladiator", "Champion")));
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Templar", "Inquisitor", "Heirophant", "Guardian")));
		ascendencyDict.add(new ArrayList<String>(Arrays.asList("Shadow", "Assassin", "Trickster", "Saboteur")));
		
		String characterName = "NotSoSlowBurn";
		
		ObjectMapperToMap obj = new ObjectMapperToMap();
		
		LinkedHashMap<String,Object> json = null;
		
		try {
			json = obj.readJsonToMap(characterName, "59e216cf34df30cb36b9fa26fb6a3d16");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<LinkedHashMap<String, Object>> itemList = (ArrayList<LinkedHashMap<String, Object>>) json.get("items");
		LinkedHashMap<String, Object> characterInfo = (LinkedHashMap<String, Object>) json.get("character"); 
		
		
		Exile newExile = parseExile(characterInfo);

		HashMap<String, Gear> items = new HashMap<String, Gear>();
		for (LinkedHashMap<String, Object> item : itemList) {
			if (item.get("inventoryId").toString().equals("MainInventory")) {
				continue;
			}
			Gear thisItem = parseGear(item);
			items.put(thisItem.getGearSlot(), thisItem);
		}
				
		newExile.setItems(items); 
		
		for (Gear item: newExile.getItems().values()) {
			for (String line: item.getItemName()) {
				System.out.println(line);
			}
			System.out.println("--------------------");
			System.out.println("Item level: " + item.getItemLevel());
			if (item.getStatBlock() != null) {
				for (String line: item.getStatBlock()) {
					System.out.println(line);
				}
			}
			if (item.getRequirements() != null) {
				System.out.println(item.getRequirements());
			}
			System.out.println("--------------------");
			if (item.getImplicitMods() != null) {
				for (String line: item.getImplicitMods()) {
					System.out.println(line);
				}
				System.out.println("--------------------");
			}

			if (item.getExplicitMods() != null) {
				for (String line: item.getExplicitMods()) {
					System.out.println(line);
				}
			}
			System.out.println();
		}
		
	}
	
	public static Gem parseGem(LinkedHashMap<String, Object> gem) {
		return new Gem();
	}
	
	public static Gear parseGear(LinkedHashMap<String, Object> gearJson) {
		Gear gear = new Gear();
		ArrayList<String> tempList = new ArrayList<String>();
		
		// add item level and rarity;
		gear.setImage((String) gearJson.get("icon"));
		gear.setRarity((Integer) gearJson.get("frameType"));
		
		
		
		// parse the header
		if (gearJson.get("name").toString().length() > 0) {
			tempList.add((String) gearJson.get("name"));
		}
		tempList.add((String) gearJson.get("typeLine"));
		gear.setItemName((ArrayList<String>) tempList.clone());
		tempList.clear();

		// parse category
		
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
		
		// parse sockets
		
		
		// parse gems in sockets
		System.out.println(gearJson.get("inventoryId"));
		gear.setGearSlot(gearJson.get("iventoryId").toString());

		
		return gear;
	}
	
	public static ArrayList<String> parseBlock(ArrayList<LinkedHashMap<String, Object>> propertyList) {
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
				statBlock.add(line);
				continue;
			} else {
				tempList = (ArrayList<Object>) tempList.get(0);
				line = (String) propertyMap.get("name") + ": ";
				line += (String) tempList.get(0);
			}
			statBlock.add(line);
		}
		
		return statBlock;
	}
	
	public static String parseReqs(ArrayList<LinkedHashMap<String, Object>> reqsList) {
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
	
	public static Exile parseExile(LinkedHashMap<String, Object> json) {
		Exile thisExile = new Exile();
		thisExile.setName(json.get("name").toString());
		
		thisExile.setBaseClass(ascendencyDict.get(Integer.parseInt(json.get("classId").toString())).get(0));
		if (Integer.parseInt(json.get("ascendancyClass").toString()) == 0) {
			thisExile.setAscendancy("None");
		} else { 
			thisExile.setAscendancy(ascendencyDict.get(Integer.parseInt(json.get("classId").toString())).get(Integer.parseInt(json.get("ascendancyClass").toString())));
		}
		thisExile.setLevel(Integer.parseInt(json.get("level").toString()));
		return thisExile;
	}
	
	
}