package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;

@Repository
public interface ArtifactSandsRepository extends JpaRepository<ArtifactSands, Integer>
{
	List<ArtifactSands> findByName(String name);
}
