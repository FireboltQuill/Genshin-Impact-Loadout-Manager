package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;

@Repository
public interface ArtifactFeatherRepository extends JpaRepository<ArtifactFeather, Integer>
{
	List<ArtifactFeather> findByName(String name);
}
