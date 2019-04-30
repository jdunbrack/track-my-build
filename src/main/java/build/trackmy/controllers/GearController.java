package build.trackmy.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import build.trackmy.models.Exile;
import build.trackmy.services.ExileService;
import build.trackmy.services.GearService;

@RestController
@RequestMapping("/itemData")
public class GearController {
	private final ExileService eServ;
	private final GearService gServ;
	
	public GearController(ExileService eServ, GearService gServ) {
		this.eServ = eServ;
		this.gServ = gServ;
	}
	
	@RequestMapping("/{slot}")
	public String getItemData(@PathVariable("slot") String gearSlot, HttpSession session) {
		Exile thisExile = eServ.findExile((Long) session.getAttribute("eid"));
		return thisExile.getItems().get(gearSlot).getDataDump();
	}
}
