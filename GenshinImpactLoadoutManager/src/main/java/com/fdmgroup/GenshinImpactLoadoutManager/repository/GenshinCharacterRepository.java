package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;

@Repository
public interface GenshinCharacterRepository extends JpaRepository<GenshinCharacter, Integer>
{
	List<GenshinCharacter> findByName(String name);
}
