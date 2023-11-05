package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactCircletRepository;

@SpringBootTest
public class ArtifactCircletServiceTest
{
	@Autowired
	private ArtifactCircletService artifactCircletService;
	
	@MockBean
	private ArtifactCircletRepository mockArtifactCircletRepo;
	
	@Test
	public void save_ArtifactCirclet()
	{
		ArtifactCirclet artifactCirclet = new ArtifactCirclet();
		artifactCircletService.save(artifactCirclet);
		verify(mockArtifactCircletRepo).save(artifactCirclet);
	}
	
	@Test
	public void delete_ArtifactCirclet()
	{
		ArtifactCirclet artifactCirclet = new ArtifactCirclet();
		int id = artifactCirclet.getId();
		artifactCircletService.delete(id);
		verify(mockArtifactCircletRepo).deleteById(id);
	}
	
	@Test
	public void getAll_ArtifactCirclets()
	{
		artifactCircletService.getAll();
		verify(mockArtifactCircletRepo).findAll();
	}

	@Test
	public void findById_ArtifactCirclet()
	{
		ArtifactCirclet artifactCirclet = new ArtifactCirclet();
		int id = artifactCirclet.getId();
		artifactCircletService.findById(id);
		verify(mockArtifactCircletRepo).findById(id);
	}

}
