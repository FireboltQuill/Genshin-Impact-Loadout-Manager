package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactSandsRepository;

@SpringBootTest
public class ArtifactSandsServiceTest
{
	@Autowired
	private ArtifactSandsService artifactSandsService;
	
	@MockBean
	private ArtifactSandsRepository mockArtifactSandsRepo;
	
	@Test
	public void save_ArtifactSands()
	{
		ArtifactSands artifactSands = new ArtifactSands();
		artifactSandsService.save(artifactSands);
		verify(mockArtifactSandsRepo).save(artifactSands);
	}
	
	@Test
	public void delete_ArtifactSands()
	{
		ArtifactSands artifactSands = new ArtifactSands();
		int id = artifactSands.getId();
		artifactSandsService.delete(id);
		verify(mockArtifactSandsRepo).deleteById(id);
	}
	
	@Test
	public void getAll_ArtifactSandss()
	{
		artifactSandsService.getAll();
		verify(mockArtifactSandsRepo).findAll();
	}

	@Test
	public void findById_ArtifactSands()
	{
		ArtifactSands artifactSands = new ArtifactSands();
		int id = artifactSands.getId();
		artifactSandsService.findById(id);
		verify(mockArtifactSandsRepo).findById(id);
	}

}
