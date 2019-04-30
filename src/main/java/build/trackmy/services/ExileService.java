package build.trackmy.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import build.trackmy.ExileListParser;
import build.trackmy.models.Exile;
import build.trackmy.models.Gear;
import build.trackmy.models.PassiveTree;
import build.trackmy.repositories.ExileRepository;

@Service
public class ExileService {
	private final ExileRepository repo;
	private final GearService serv;

	public ExileService(ExileRepository repo, GearService serv) {
		this.repo = repo;
		this.serv = serv;
	}
	
	public Exile findByName(String name) {
		return repo.findByName(name);
	}
	
	public Exile findExile(Long id) {
		Optional<Exile> opt = repo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public List<Exile> findAllByAscendancy(String ascendancy) {
		return repo.findAllByAscendancy(ascendancy);
	}
	
	public Exile addExile(Exile e) {
		return repo.save(e);
	}
	
	public Exile updateExile(Exile e) {
		return repo.save(e);
	}
	
	public void deleteExile(Exile e) {
		repo.delete(e);
	}
	
	public void deleteExileById(Long id) {
		repo.deleteById(id);
	}
	
	public List<Exile> findTopByAscendancy(String ascendancy) {
		return repo.findAllByAscendancyOrderByLevelDesc(ascendancy);
	}
}
