package build.trackmy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ObjectMapperToMap {
	private final String BASE_URL;
	
	public ObjectMapperToMap() {
		super();
		this.BASE_URL = "https://www.pathofexile.com/character-window";
	}
		
	public LinkedHashMap<String, Object> readJsonToMap(String urlString, String POESESSID) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		System.setProperty("http.agent", "Chrome");
		URL url = new URL(BASE_URL + urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Cookie", "POESESSID=" + POESESSID);
		con.setRequestProperty("Accept", "text/html, application/json, text/json, text/text");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0");
		
		con.setInstanceFollowRedirects(true);
		
		LinkedHashMap<String,Object> equipMap = (LinkedHashMap<String, Object>) objectMapper.readValue(con.getInputStream(), Map.class);

		con.getInputStream().close();
		con.disconnect();
		return equipMap;
	}
	
}
