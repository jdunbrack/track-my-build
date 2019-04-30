package build.trackmy.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import build.trackmy.models.Gear;
import build.trackmy.models.Gem;

@Repository
public interface GemRepository extends CrudRepository<Gem, Long> {
	List<Gem> findAllByGear(Gear gear);
}
