package build.trackmy.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import build.trackmy.models.Exile;
import build.trackmy.models.Gear;

@Repository
public interface GearRepository extends CrudRepository<Gear, Long> {
	List<Gear> findAllByExile(Exile exile);
	Gear findByItemNameEqualsAndItemLevelEquals(String name, int ilvl);
}
