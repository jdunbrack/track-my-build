package build.trackmy.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import build.trackmy.models.Exile;
import build.trackmy.models.GearSwap;

@Repository
public interface GearSwapRepository extends CrudRepository<GearSwap, Long> {
	
	List<GearSwap> findAllByExileOrderByCreatedAtAsc(Exile exile);
	List<GearSwap> findAllByExileAndLevelLessThan(Exile exile, int level);
	GearSwap findTopByExileOrderByCreatedAtAsc(Exile exile);
}
