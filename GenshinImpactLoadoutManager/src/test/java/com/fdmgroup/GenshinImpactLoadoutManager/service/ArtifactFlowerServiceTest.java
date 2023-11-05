package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactFlowerRepository;

@SpringBootTest
public class ArtifactFlowerServiceTest
{
	@Autowired
	private ArtifactFlowerService artifactFlowerService;
	
	@MockBean
	private ArtifactFlowerRepository mockArtifactFlowerRepo;
	
	@Test
	public void save_ArtifactFlower()
	{
		ArtifactFlower artifactFlower = new ArtifactFlower();
		artifactFlowerService.save(artifactFlower);
		verify(mockArtifactFlowerRepo).save(artifactFlower);
	}
	
	@Test
	public void delete_ArtifactFlower()
	{
		ArtifactFlower artifactFlower = new ArtifactFlower();
		int id = artifactFlower.getId();
		artifactFlowerService.delete(id);
		verify(mockArtifactFlowerRepo).deleteById(id);
	}
	
	@Test
	public void getAll_ArtifactFlowers()
	{
		artifactFlowerService.getAll();
		verify(mockArtifactFlowerRepo).findAll();
	}

	@Test
	public void findById_ArtifactFlower()
	{
		ArtifactFlower artifactFlower = new ArtifactFlower();
		int id = artifactFlower.getId();
		artifactFlowerService.findById(id);
		verify(mockArtifactFlowerRepo).findById(id);
	}

}
