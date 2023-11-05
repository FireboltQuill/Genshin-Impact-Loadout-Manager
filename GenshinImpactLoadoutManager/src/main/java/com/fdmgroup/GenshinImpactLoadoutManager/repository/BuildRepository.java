package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;

@Repository
public interface BuildRepository extends JpaRepository<Build, Integer>
{
	
	List<Build> findByCharacterId(int id);

	List<Build> findByUsersId(int id);
	
	List<Build> findByWeaponId(int id);
	
	List<Build> findByFlowerId(int id);

	List<Build> findByFeatherId(int id);

	List<Build> findBySandsId(int id);

	List<Build> findByGobletId(int id);

	List<Build> findByCircletId(int id);
}
