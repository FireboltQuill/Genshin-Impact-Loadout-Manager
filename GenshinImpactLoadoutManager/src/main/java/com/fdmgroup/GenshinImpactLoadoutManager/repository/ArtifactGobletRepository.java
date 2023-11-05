package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;

@Repository
public interface ArtifactGobletRepository extends JpaRepository<ArtifactGoblet, Integer>
{
	List<ArtifactGoblet> findByName(String name);
}
