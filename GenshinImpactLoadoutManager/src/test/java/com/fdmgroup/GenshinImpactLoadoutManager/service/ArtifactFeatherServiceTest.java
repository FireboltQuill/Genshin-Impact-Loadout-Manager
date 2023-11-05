package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactFeatherRepository;

@SpringBootTest
public class ArtifactFeatherServiceTest
{
	@Autowired
	private ArtifactFeatherService artifactFeatherService;
	
	@MockBean
	private ArtifactFeatherRepository mockArtifactFeatherRepo;
	
	@Test
	public void save_ArtifactFeather()
	{
		ArtifactFeather artifactFeather = new ArtifactFeather();
		artifactFeatherService.save(artifactFeather);
		verify(mockArtifactFeatherRepo).save(artifactFeather);
	}
	
	@Test
	public void delete_ArtifactFeather()
	{
		ArtifactFeather artifactFeather = new ArtifactFeather();
		int id = artifactFeather.getId();
		artifactFeatherService.delete(id);
		verify(mockArtifactFeatherRepo).deleteById(id);
	}
	
	@Test
	public void getAll_ArtifactFeathers()
	{
		artifactFeatherService.getAll();
		verify(mockArtifactFeatherRepo).findAll();
	}

	@Test
	public void findById_ArtifactFeather()
	{
		ArtifactFeather artifactFeather = new ArtifactFeather();
		int id = artifactFeather.getId();
		artifactFeatherService.findById(id);
		verify(mockArtifactFeatherRepo).findById(id);
	}

}
