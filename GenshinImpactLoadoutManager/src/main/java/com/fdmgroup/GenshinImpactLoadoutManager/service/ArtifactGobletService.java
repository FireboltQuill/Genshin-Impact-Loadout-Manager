package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactGobletRepository;

@Service
public class ArtifactGobletService
{
	
	private ArtifactGobletRepository ArtifactGobletRepo;

	@Autowired
	public ArtifactGobletService(ArtifactGobletRepository ArtifactGobletRepo)
	{
		super();
		this.ArtifactGobletRepo = ArtifactGobletRepo;
	}
	
	@Transactional
	public void save(ArtifactGoblet ArtifactGoblet)
	{
		ArtifactGobletRepo.save(ArtifactGoblet);
	}
	
	@Transactional
	public void delete(int id)
	{
		ArtifactGobletRepo.deleteById(id);
	}
	
	public List<ArtifactGoblet> getAll()
	{
		return ArtifactGobletRepo.findAll();
	}
	
	public ArtifactGoblet findById(int id)
	{
		return ArtifactGobletRepo.findById(id).orElse(null);
	}

}
