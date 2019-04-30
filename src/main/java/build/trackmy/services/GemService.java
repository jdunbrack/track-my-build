package build.trackmy.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import build.trackmy.models.Gem;
import build.trackmy.repositories.GemRepository;

@Service
public class GemService {
	private final GemRepository repo;
	
	public GemService(GemRepository repo) {
		this.repo = repo;
	}

	public Gem addGem(Gem gem) {
		return repo.save(gem);
	}
	
	public Gem findGem(Long id) {
		Optional<Gem> opt = repo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public void destroyGem(Gem gem) {
		repo.delete(gem);
	}
}
