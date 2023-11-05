package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactSandsRepository;

@Service
public class ArtifactSandsService
{
	
	private ArtifactSandsRepository ArtifactSandsRepo;

	@Autowired
	public ArtifactSandsService(ArtifactSandsRepository ArtifactSandsRepo)
	{
		super();
		this.ArtifactSandsRepo = ArtifactSandsRepo;
	}
	
	@Transactional
	public void save(ArtifactSands ArtifactSands)
	{
		ArtifactSandsRepo.save(ArtifactSands);
	}
	
	@Transactional
	public void delete(int id)
	{
		ArtifactSandsRepo.deleteById(id);
	}
	
	public List<ArtifactSands> getAll()
	{
		return ArtifactSandsRepo.findAll();
	}
	
	public ArtifactSands findById(int id)
	{
		return ArtifactSandsRepo.findById(id).orElse(null);
	}

}
