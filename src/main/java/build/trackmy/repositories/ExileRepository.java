package build.trackmy.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import build.trackmy.models.Exile;

@Repository
public interface ExileRepository extends CrudRepository<Exile, Long> {
	Exile findByName(String name);
	List<Exile> findAllByBaseClass(String baseClass);
	List<Exile> findAllByAscendancy(String ascendancy);
	List<Exile> findAllByAscendancyOrderByLevelDesc(String ascendancy);
}
