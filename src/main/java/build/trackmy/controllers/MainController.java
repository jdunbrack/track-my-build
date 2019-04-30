package build.trackmy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import build.trackmy.ExileListParser;
import build.trackmy.GearCompare;
import build.trackmy.JsonParser;
import build.trackmy.ObjectMapperToMap;
import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.models.GearSwap;
import build.trackmy.models.Gem;
import build.trackmy.models.PassiveTree;
import build.trackmy.services.ExileService;
import build.trackmy.services.GearService;
import build.trackmy.services.GearSwapService;
import build.trackmy.services.GemService;
import build.trackmy.services.PassiveTreeService;

@Controller
@RequestMapping("/")
public class MainController {
	
	private final ExileService eServ;
	private final GearService gServ;
	private final GemService gemServ;
	private final PassiveTreeService ptServ;
	private final GearSwapService swapServ;
	private final ObjectMapperToMap obj;
	
	public MainController(ObjectMapperToMap obj, GemService gemServ, GearSwapService swapServ, ExileService eServ, PassiveTreeService ptServ, GearService gServ) {
		this.obj = obj;
		this.ptServ = ptServ;
		this.eServ = eServ;
		this.swapServ = swapServ;
		this.gServ = gServ;
		this.gemServ = gemServ;
	}
	
	@RequestMapping("")
	public String index(HttpSession session) {
		if (session.getAttribute("POESESSID") != null) {
			return "redirect:/exileList";
		}
		return "index.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/setSessId", method=RequestMethod.POST)
	public String setSessId(@RequestParam("POESESSID") String POESESSID, HttpSession session) {
		session.setAttribute("POESESSID", POESESSID);
		return "redirect:/exileList";
	}
	
	@RequestMapping("/exileList")
	public String exileList(Model model, HttpSession session) {
		if (session.getAttribute("POESESSID") == null) {
			return "redirect:/";
		}
		session.removeAttribute("characterName");
		ArrayList<Exile> exileList = new ArrayList<Exile>();
		try {
			exileList = ExileListParser.pullExiles((String) session.getAttribute("POESESSID"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("exiles", exileList);
		return "list.jsp";
	}
	
	@RequestMapping("/setExile/{name}")
	public String setExile(Model model, HttpSession session, @PathVariable("name") String characterName) {
		session.setAttribute("characterName", characterName);
		return "redirect:/sheet";
	}
	
	@RequestMapping("/sheet")
	public String characterSheet(Model model, HttpSession session, @ModelAttribute("passiveTree") PassiveTree passiveTree) throws InterruptedException {
		if (session.getAttribute("characterName") == null) {
			if (session.getAttribute("POESESSID") == null) {
				return "redirect:/";
			}
			return "redirect:/exileList";
		}
		model.addAttribute("exile", null);
		String characterName = (String) session.getAttribute("characterName");
		Exile thisExile = eServ.findByName(characterName);
		String POESESSID = (String) session.getAttribute("POESESSID");
		JsonParser jP = new JsonParser(characterName, POESESSID);
		
		// if this Exile hasn't been tracked yet...
		if (thisExile == null) {
			
			// get exile data from pathofexile.com and make/save an Exile
			jP = new JsonParser(characterName, POESESSID);
			thisExile = jP.parseAll();
			
			// save exile to force hibernate to populate id field
			eServ.addExile(thisExile);
			
			LinkedHashMap<String, ArrayList<Gem>> gemMap = jP.parseGems();
			
			// ensure gear is properly associated and saved to db
			Map<String, Gear> itemList = thisExile.getItems();
			for (Gear item: itemList.values()) {
				item.setExile(thisExile);
				gServ.addGear(item);
//				if (gemMap.get(item.getGearSlot()) != null) {
//					for (Gem gem: gemMap.get(item.getGearSlot())) {
//						gem.setGear(item);
//						gemServ.addGem(gem);
//					}
//				}
			}
			
			eServ.addExile(thisExile);
			
			// save current passive tree
			PassiveTree newTree = new PassiveTree();
			try {
				newTree = ExileListParser.pullPassiveTree((String) session.getAttribute("POESESSID"), thisExile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ptServ.addPassiveTree(newTree);
			HashMap<Integer,PassiveTree> trees = new HashMap<Integer,PassiveTree>();
			trees.put(thisExile.getLevel(), newTree);
			thisExile.setPassiveTrees((Map<Integer, PassiveTree>) trees);
			eServ.addExile(thisExile);
		}
		

		// check if passive tree in DB is latest
		PassiveTree latestTree = ptServ.getLatestPassiveTree(thisExile);
		PassiveTree currentTree = new PassiveTree();
		try {
			currentTree = ExileListParser.pullPassiveTree((String) session.getAttribute("POESESSID"), thisExile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (latestTree != null && currentTree != null) {
			if (latestTree.getLevel() != currentTree.getLevel()) {
				ptServ.addPassiveTree(currentTree);
			}
		}
		Exile tempExile = jP.parseAll();
		List<GearSwap> newGearSwaps = (List<GearSwap>) GearCompare.createGearSwapList(thisExile, tempExile.getItems());
		
		// check for equipment updates
		
		eServ.updateExile(thisExile);
		for (Gear item: thisExile.getItems().values()) {
			if (!GearCompare.itemIsEqual(tempExile.getItems().get(item.getGearSlot()), item)) {
				if (item.getSwappedFrom() != null) {
					swapServ.addGearSwap(item.getSwappedFrom());
				} else if (item.getSwapTo() != null) {
					swapServ.addGearSwap(item.getSwapTo());
				}
				item.setExile(thisExile);
				gServ.addGear(item);
			}
		}	

		if (newGearSwaps != null && newGearSwaps.size() > 0) {
			List<GearSwap> oldGearSwaps = (List<GearSwap>) thisExile.getGearSwaps();
			for (GearSwap swap: newGearSwaps) {
				swapServ.addGearSwap(swap);
				gServ.addGear(swap.getNewGear());
				gServ.addGear(swap.getOldGear());
				oldGearSwaps.add(swap);
			}
			thisExile.setGearSwaps(oldGearSwaps);
			model.addAttribute("currentBuild", tempExile);
		}
		
		
		// hashmap gearSwaps with level as key to make organization easier in the .jsp
		
		HashMap<Integer, List<GearSwap>> swapMap = new HashMap<Integer,List<GearSwap>>();
		for (GearSwap swap: swapServ.findGearSwapsByExile(thisExile)) {
			if (swapMap.putIfAbsent(swap.getLevel(), new ArrayList<GearSwap>(Arrays.asList(swap))) != null && swap.getNewGear() != null) {
				List<GearSwap> swapList = swapMap.get(swap.getLevel());
				swapList.add(swap);
				swapMap.put(swap.getLevel(), swapList);
			}
		}
		
		model.addAttribute("milestoneLevels", GearCompare.getMilestoneLevels(thisExile));
		model.addAttribute("gearSwaps", swapMap);
		model.addAttribute("exile", thisExile);
		return "sheet.jsp";
	}
	
	@RequestMapping(value="/passives", method=RequestMethod.GET)
	public void passiveTree(@RequestParam("exile") Exile exile, @RequestParam("level") String level, HttpServletResponse httpServletResponse) {
		String url = "http://pathofexile.com" + exile.getPassiveTrees().get(Integer.parseInt(level)).getLink();
		httpServletResponse.setHeader("location", url);
		httpServletResponse.setStatus(302);
	}
		
}
