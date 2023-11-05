package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactFeatherRepository;

@Service
public class ArtifactFeatherService
{
	
	private ArtifactFeatherRepository ArtifactFeatherRepo;

	@Autowired
	public ArtifactFeatherService(ArtifactFeatherRepository ArtifactFeatherRepo)
	{
		super();
		this.ArtifactFeatherRepo = ArtifactFeatherRepo;
	}
	
	@Transactional
	public void save(ArtifactFeather ArtifactFeather)
	{
		ArtifactFeatherRepo.save(ArtifactFeather);
	}
	
	@Transactional
	public void delete(int id)
	{
		ArtifactFeatherRepo.deleteById(id);
	}
	
	public List<ArtifactFeather> getAll()
	{
		return ArtifactFeatherRepo.findAll();
	}
	
	public ArtifactFeather findById(int id)
	{
		return ArtifactFeatherRepo.findById(id).orElse(null);
	}

}
