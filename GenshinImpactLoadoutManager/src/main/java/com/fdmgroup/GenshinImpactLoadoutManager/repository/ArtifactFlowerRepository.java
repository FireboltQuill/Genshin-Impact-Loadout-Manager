package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;

@Repository
public interface ArtifactFlowerRepository extends JpaRepository<ArtifactFlower, Integer>
{
	List<ArtifactFlower> findByName(String name);
}
