package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactGobletRepository;

@SpringBootTest
public class ArtifactGobletServiceTest
{
	@Autowired
	private ArtifactGobletService artifactGobletService;
	
	@MockBean
	private ArtifactGobletRepository mockArtifactGobletRepo;
	
	@Test
	public void save_ArtifactGoblet()
	{
		ArtifactGoblet artifactGoblet = new ArtifactGoblet();
		artifactGobletService.save(artifactGoblet);
		verify(mockArtifactGobletRepo).save(artifactGoblet);
	}
	
	@Test
	public void delete_ArtifactGoblet()
	{
		ArtifactGoblet artifactGoblet = new ArtifactGoblet();
		int id = artifactGoblet.getId();
		artifactGobletService.delete(id);
		verify(mockArtifactGobletRepo).deleteById(id);
	}
	
	@Test
	public void getAll_ArtifactGoblets()
	{
		artifactGobletService.getAll();
		verify(mockArtifactGobletRepo).findAll();
	}

	@Test
	public void findById_ArtifactGoblet()
	{
		ArtifactGoblet artifactGoblet = new ArtifactGoblet();
		int id = artifactGoblet.getId();
		artifactGobletService.findById(id);
		verify(mockArtifactGobletRepo).findById(id);
	}

}
