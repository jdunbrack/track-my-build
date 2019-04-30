package build.trackmy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.models.GearSwap;
import build.trackmy.services.GearService;
import build.trackmy.services.GearSwapService;

public class GearCompare {
	
	private static GearSwapService swapServ;
	private static GearService gServ; 
	
	public GearCompare(GearSwapService swapServ, GearService gServ) {
		this.swapServ = swapServ;
		this.gServ = gServ;
	}
	
	public static List<Gear> listUpdatedEquipment(Map<String, Gear> oldGear, Map<String, Gear> newGear) {
		ArrayList<Gear> changeList = new ArrayList<Gear>();
		
		for (Gear oldItem: oldGear.values()) {
			if (oldItem.getDataDump().equals(newGear.get(oldItem.getGearSlot()).getDataDump())) {
				continue;
			} else {
				changeList.add(newGear.get(oldItem.getGearSlot()));
			}
		}
		
		return changeList;
	}
	
	public static List<GearSwap> createGearSwapList(Exile exile, Map<String, Gear> newGear) {
		
		Map<String, Gear> oldGear = exile.getItems();
		List<GearSwap> swapsList = exile.getGearSwaps();
		
		if (swapsList == null || swapsList.size() == 0) {
			return null;
		}
		
		// update all "oldGear" items to the most recent set of swapped items
		for (GearSwap swap: swapsList) {
			if (swap.getNewGear() != null && swap.getOldGear() != null) {
				oldGear.put(swap.getNewGear().getGearSlot(), swap.getNewGear());
			}
		}
		
		swapsList.clear();
		
		for (Gear oldItem: oldGear.values()) {
			if (!itemIsEqual(oldItem, newGear.get(oldItem.getGearSlot()))) {
				GearSwap newSwap = new GearSwap(exile, exile.getLevel(), oldItem, newGear.get(oldItem.getGearSlot()));
				swapsList.add(newSwap);
			}
		}
		
		return swapsList;
	}
	
	public static boolean itemIsEqual(Gear oldGear, Gear newGear) {
		
		if (!oldGear.getDataDump().equals(newGear.getDataDump())) {
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Integer> getMilestoneLevels(Exile exile) {
		ArrayList<Integer> levelList = new ArrayList<Integer>();
		
		List<GearSwap> allSwaps = exile.getGearSwaps();
		
		if (allSwaps == null || allSwaps.size() == 0) {
			return null;
		}
		
		for (GearSwap swap: allSwaps) {
			if (!levelList.contains(swap.getLevel())) {
				levelList.add(swap.getLevel());
			}
		}
		
		Collections.sort(levelList);
		
		return levelList;
	}
}
