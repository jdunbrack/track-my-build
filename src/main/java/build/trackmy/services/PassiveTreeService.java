package build.trackmy.services;

import org.springframework.stereotype.Service;

import build.trackmy.models.Exile;
import build.trackmy.models.PassiveTree;
import build.trackmy.repositories.PassiveTreeRepository;

@Service
public class PassiveTreeService {
	private final PassiveTreeRepository repo;
	
	public PassiveTreeService(PassiveTreeRepository repo) {
		this.repo = repo;
	}
	
	public PassiveTree addPassiveTree(PassiveTree passiveTree) {
		return repo.save(passiveTree);
	}
	
	public void destroyPassiveTree(PassiveTree passiveTree) {
		repo.delete(passiveTree);
	}
	
	public PassiveTree getLatestPassiveTree(Exile exile) {
		return repo.findTopByExileOrderByLevelDesc(exile);
	}
	
	public PassiveTree getTreeAtLevel(Exile exile, int level) {
		return repo.findByExileAndLevelEquals(exile, level);
	}
}
