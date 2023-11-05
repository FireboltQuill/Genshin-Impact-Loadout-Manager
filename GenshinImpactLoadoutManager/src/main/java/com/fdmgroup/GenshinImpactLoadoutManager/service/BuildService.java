package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.BuildRepository;

@Service
public class BuildService
{
	
	private BuildRepository buildRepo;

	@Autowired
	public BuildService(BuildRepository buildRepo)
	{
		super();
		this.buildRepo = buildRepo;
	}
	
	@Transactional
	public void save(Build build)
	{
		buildRepo.save(build);
	}
	
	@Transactional
	public void delete(int id)
	{
		buildRepo.deleteById(id);
	}
	
	public List<Build> getAll()
	{
		return buildRepo.findAll();
	}
	
	public Build findById(int id)
	{
		return buildRepo.findById(id).orElse(null);
	}
	
	
	
	public List<Build> findByCharacterId(int id)
	{
		return buildRepo.findByCharacterId(id);
	}
	
	public List<Build> findByUsersId(int id)
	{
		return buildRepo.findByUsersId(id);
	}
	
	public List<Build> findByWeaponId(int id)
	{
		return buildRepo.findByWeaponId(id);
	}
	
	public List<Build> findByArtifactFeatherId(int id)
	{
		return buildRepo.findByFeatherId(id);
	}
	
	public List<Build> findByArtifactFlowerId(int id)
	{
		return buildRepo.findByFlowerId(id);
	}
	
	public List<Build> findByArtifactSandsId(int id)
	{
		return buildRepo.findBySandsId(id);
	}
	
	public List<Build> findByArtifactGobletId(int id)
	{
		return buildRepo.findByGobletId(id);
	}
	
	public List<Build> findByArtifactCircletId(int id)
	{
		return buildRepo.findByCircletId(id);
	}
	
	

}
