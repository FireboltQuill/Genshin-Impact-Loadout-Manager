package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;

@Repository
public interface ArtifactCircletRepository extends JpaRepository<ArtifactCirclet, Integer>
{
	List<ArtifactCirclet> findByName(String name);
}
