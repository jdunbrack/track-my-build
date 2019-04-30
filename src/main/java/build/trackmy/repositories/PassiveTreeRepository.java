package build.trackmy.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import build.trackmy.models.Exile;
import build.trackmy.models.PassiveTree;

@Repository
public interface PassiveTreeRepository extends CrudRepository<PassiveTree,Long> {

	List<PassiveTree> findAllByExileOrderByLevelAsc(Exile exile);
	PassiveTree findTopByExileOrderByLevelDesc(Exile exile);
	PassiveTree findByExileAndLevelEquals(Exile exile, int level);
}
