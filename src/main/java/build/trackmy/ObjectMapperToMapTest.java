package build.trackmy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.models.Gem;

public class ObjectMapperToMapTest {
	
	public static void main(String[] args)  {
		HashMap<Integer, ArrayList<String>> ascendencyDict = new HashMap<Integer, ArrayList<String>>();
		
		
		
		ObjectMapperToMap obj = new ObjectMapperToMap();
		
		LinkedHashMap<String,Object> json = null;
		
		try {
			json = obj.readJsonToMap("https://www.pathofexile.com/character-window/get-items?character=NotSoSlowBurn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<LinkedHashMap<String, Object>> itemList = (ArrayList<LinkedHashMap<String, Object>>) json.get("items");
		LinkedHashMap<String, Object> characterInfo = (LinkedHashMap<String, Object>) json.get("character"); 
		
		
		Exile newExile = parseExile(characterInfo);
		
		System.out.println(newExile.getName());
		System.out.println(newExile.getBaseClass());
		System.out.println(newExile.getAscendency());
		System.out.println(newExile.getLevel());
		
		
//		ArrayList<Gear> items = new ArrayList<Gear>();
//		
//		for (LinkedHashMap<String, Object> item : itemList) {
//			items.add(parseGear(item));
//		}
//		
//		newExile.setItems(items); 
		
		
		
	}
	
	public static Gem parseGem(LinkedHashMap<String, Object> gem) {
		return new Gem();
	}
	
	public static Gear parseGear(LinkedHashMap<String, Object> gearJson) {
		Gear gear = new Gear();
		
		gear.setImage((String) gearJson.get("icon"));
		gear.setRarity((Integer) gearJson.get("frameType"));
		
		ArrayList<String> tempList = gear.getItemName();
		
		// parse the header
		tempList.add((String) gearJson.get("name"));
		tempList.add((String) gearJson.get("typeLine"));
		gear.setItemName(tempList);
		tempList.clear();
		
		
		// parse category
		
		// parse the statblock from ilvl through requirements
		tempList.add("Item Level: " + (String) gearJson.get("ilvl"));
		tempList.addAll(parseBlock((ArrayList<LinkedHashMap<String, Object>>) gearJson.get("properties")));
		tempList.add("break");
		
		// parse requirements
		tempList.add(parseReqs((ArrayList<LinkedHashMap<String, Object>>) gearJson.get("requirements")));

		for (String line: tempList) {
			System.out.println(line);
		}
		
		// parse implicit modifiers
		
		
		// parse explicit modifiers
		
		
		// parse sockets
		
		
		// parse gems in sockets
		
		
		tempList.clear();

		
		
		
		return new Gear();
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
			tempList = (ArrayList<Object>) tempList.get(0);
			line = (String) propertyMap.get("name") + ": ";
			line += (String) tempList.get(0);
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
			reqsLine = (String) reqsMap.get("name") + " " + (String) tempList.get(0);
			if (i != reqsList.size() - 1) {
				reqsLine += ", ";
			}
		}
		
		return reqsLine;
		
	}
	
	public static Exile parseExile(LinkedHashMap<String, Object> json) {
		Exile thisExile = new Exile();
		thisExile.setName(json.get("name").toString());
		thisExile.setBaseClass(Integer.parseInt(json.get("classId").toString()));
		thisExile.setAscendency(Integer.parseInt(json.get("ascendancyClass").toString()));
		thisExile.setLevel(Integer.parseInt(json.get("level").toString()));
		return thisExile;
	}
	
	
}