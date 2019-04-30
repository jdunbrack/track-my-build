package build.trackmy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import build.trackmy.models.Exile;
import build.trackmy.models.GearSwap;
import build.trackmy.repositories.GearSwapRepository;

@Service
public class GearSwapService {
	private final GearSwapRepository repo;
	
	public GearSwapService(GearSwapRepository repo) {
		this.repo = repo;
	}
	
	public GearSwap addGearSwap(GearSwap gs) {
		return repo.save(gs);
	}
	
	public List<GearSwap> findGearSwapsByExile(Exile exile) {
		return repo.findAllByExileOrderByCreatedAtAsc(exile);
	}
	
	public List<GearSwap> findGearSwapsBeforeLevel(Exile exile, int level) {
		return repo.findAllByExileAndLevelLessThan(exile, level);
	}
	
	public void destroyGearSwap(GearSwap gs) {
		repo.delete(gs);
	}
	
	public GearSwap findFirstGearSwap(Exile exile) {
		return repo.findTopByExileOrderByCreatedAtAsc(exile);
	}
}
