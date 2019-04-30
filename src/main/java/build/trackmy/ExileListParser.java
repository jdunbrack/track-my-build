package build.trackmy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import build.trackmy.models.Exile;
import build.trackmy.models.PassiveTree;

public class ExileListParser {
	private static ArrayList<ArrayList<String>> ascendancyDict;
	
	public static ArrayList<Exile> pullExiles(String POESESSID) throws IOException {
		ObjectMapper obj = new ObjectMapper();
		ArrayList<Exile> exileList = new ArrayList<Exile>();
		ArrayList<LinkedHashMap<String,Object>> jsonData = new ArrayList<LinkedHashMap<String,Object>>();
		ascendancyDict = new ArrayList<ArrayList<String>>();
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Scion", "Ascendant")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Marauder", "Juggernaut", "Berserker", "Chieftan")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Ranger", "Raider", "Deadeye", "Pathfinder")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Witch", "Occultist", "Elementalist", "Necromancer")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Duelist", "Slayer", "Gladiator", "Champion")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Templar", "Inquisitor", "Heirophant", "Guardian")));
		ascendancyDict.add(new ArrayList<String>(Arrays.asList("Shadow", "Assassin", "Trickster", "Saboteur")));
		
		System.setProperty("http.agent", "Chrome");
		URL url = new URL("https://www.pathofexile.com/character-window/get-characters");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Cookie", "POESESSID=" + POESESSID);
		con.setRequestProperty("Accept", "text/html, application/json, text/json, text/text");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0");
		con.setInstanceFollowRedirects(true);

		jsonData = (ArrayList<LinkedHashMap<String,Object>>) obj.readValue(con.getInputStream(), new TypeReference<ArrayList<LinkedHashMap<String, Object>>>(){});
				
		for (LinkedHashMap<String,Object> exileData: jsonData) {
			exileList.add(parseExile(exileData));
		}
				
		return exileList;
	}
	
	public static PassiveTree pullPassiveTree(String POESESSID, Exile exile) throws IOException {
		String link;

		System.setProperty("http.agent", "Chrome");
		URL url = new URL("https://www.pathofexile.com/character-window/view-passive-skill-tree?character=" + exile.getName());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Cookie", "POESESSID=" + POESESSID);
		con.setRequestProperty("Accept", "text/html, application/json, text/json, text/text");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0");
		con.setInstanceFollowRedirects(false);
		
		con.connect();
		link = con.getHeaderField("location");
		con.disconnect();
		
		return new PassiveTree(exile, exile.getLevel(), link);
	}
	
	public static Exile parseExile(LinkedHashMap<String, Object> json) {
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
}
