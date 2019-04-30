package build.trackmy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.repositories.GearRepository;

@Service
public class GearService {
	private final GearRepository repo;
	
	public GearService(GearRepository repo) {
		this.repo = repo;
	}
	
	public Gear addGear(Gear g) {
		return repo.save(g);
	}
	
	public Gear updateGear(Gear g) {
		return repo.save(g);
	}
	
	public Gear findGear(Long id) {
		Optional<Gear> opt = repo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public List<Gear> findGearByExile(Exile e) {
		return repo.findAllByExile(e);
	}
	
	public void deleteGear(Gear g) {
		repo.delete(g);
	}
	
	public void deleteGear(Long id) {
		repo.deleteById(id);
	}
	
	public Gear findByNameAndIlvl(String name, int ilvl) {
		return repo.findByItemNameEqualsAndItemLevelEquals(name, ilvl);
	}
}
