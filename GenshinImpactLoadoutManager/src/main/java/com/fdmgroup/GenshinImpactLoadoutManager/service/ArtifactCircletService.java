package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactCircletRepository;

@Service
public class ArtifactCircletService
{
	
	private ArtifactCircletRepository ArtifactCircletRepo;

	@Autowired
	public ArtifactCircletService(ArtifactCircletRepository ArtifactCircletRepo)
	{
		super();
		this.ArtifactCircletRepo = ArtifactCircletRepo;
	}
	
	@Transactional
	public void save(ArtifactCirclet ArtifactCirclet)
	{
		ArtifactCircletRepo.save(ArtifactCirclet);
	}
	
	@Transactional
	public void delete(int id)
	{
		ArtifactCircletRepo.deleteById(id);
	}
	
	public List<ArtifactCirclet> getAll()
	{
		return ArtifactCircletRepo.findAll();
	}
	
	public ArtifactCirclet findById(int id)
	{
		return ArtifactCircletRepo.findById(id).orElse(null);
	}

}
