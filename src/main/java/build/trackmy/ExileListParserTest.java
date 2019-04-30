package build.trackmy;

import java.io.IOException;
import java.util.ArrayList;

import build.trackmy.models.Exile;

public class ExileListParserTest {

	public static void main(String[] args) {
		ArrayList<Exile> exiles = new ArrayList<Exile>();
		try {
			exiles = ExileListParser.pullExiles("59e216cf34df30cb36b9fa26fb6a3d16");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Exile exile: exiles) {
			String out = exile.getName() + ", a level " + exile.getLevel() + " " + exile.getBaseClass() + "/" + exile.getAscendancy();
			System.out.println(out);
		}
		
	}

}
