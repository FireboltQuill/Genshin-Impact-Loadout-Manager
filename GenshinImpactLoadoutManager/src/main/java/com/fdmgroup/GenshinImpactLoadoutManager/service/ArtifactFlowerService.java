package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactFlowerRepository;

@Service
public class ArtifactFlowerService
{
	
	private ArtifactFlowerRepository ArtifactFlowerRepo;

	@Autowired
	public ArtifactFlowerService(ArtifactFlowerRepository ArtifactFlowerRepo)
	{
		super();
		this.ArtifactFlowerRepo = ArtifactFlowerRepo;
	}
	
	@Transactional
	public void save(ArtifactFlower ArtifactFlower)
	{
		ArtifactFlowerRepo.save(ArtifactFlower);
	}
	
	@Transactional
	public void delete(int id)
	{
		ArtifactFlowerRepo.deleteById(id);
	}
	
	public List<ArtifactFlower> getAll()
	{
		return ArtifactFlowerRepo.findAll();
	}
	
	public ArtifactFlower findById(int id)
	{
		return ArtifactFlowerRepo.findById(id).orElse(null);
	}

}
