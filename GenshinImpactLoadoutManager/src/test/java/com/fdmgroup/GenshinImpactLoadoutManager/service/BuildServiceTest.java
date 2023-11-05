package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Users;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Weapon;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.BuildRepository;

@SpringBootTest
public class BuildServiceTest
{
	@Autowired
	private BuildService buildService;

	@MockBean
	private BuildRepository mockBuildRepo;

	GenshinCharacter genshinCharacter;
	Users user;
	Weapon weapon;
	ArtifactFeather artifactFeather;
	ArtifactFlower artifactFlower;
	ArtifactSands artifactSands;
	ArtifactGoblet artifactGoblet;
	ArtifactCirclet artifactCirclet;
	Build build;

	@BeforeEach
	public void setup()
	{
		genshinCharacter = new GenshinCharacter();
		user = new Users();
		weapon = new Weapon();
		artifactFeather = new ArtifactFeather();
		artifactFlower = new ArtifactFlower();
		artifactSands = new ArtifactSands();
		artifactGoblet = new ArtifactGoblet();
		artifactCirclet = new ArtifactCirclet();
		build = new Build(genshinCharacter, user, artifactFlower, artifactFeather, artifactSands, artifactGoblet,
				artifactCirclet, weapon);
	}

	@Test
	public void save_Build()
	{
		buildService.save(build);
		verify(mockBuildRepo).save(build);
	}

	@Test
	public void delete_Build()
	{
		int id = build.getId();
		buildService.delete(id);
		verify(mockBuildRepo).deleteById(id);
	}

	@Test
	public void getAll_Builds()
	{
		buildService.getAll();
		verify(mockBuildRepo).findAll();
	}

	@Test
	public void findById_Build()
	{
		int id = build.getId();
		buildService.findById(id);
		verify(mockBuildRepo).findById(id);
	}

	@Test
	public void findByCharacterId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByCharacterId(id);
		verify(mockBuildRepo).findByCharacterId(id);
	}

	@Test
	public void findByUsersId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByUsersId(id);
		verify(mockBuildRepo).findByUsersId(id);
	}

	@Test
	public void findByWeaponId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByWeaponId(id);
		verify(mockBuildRepo).findByWeaponId(id);
	}

	@Test
	public void findByArtifactFeatherId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByArtifactFeatherId(id);
		verify(mockBuildRepo).findByFeatherId(id);
	}

	@Test
	public void findByArtifactFlowerId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByArtifactFlowerId(id);
		verify(mockBuildRepo).findByFlowerId(id);
	}

	@Test
	public void findByArtifactSandsId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByArtifactSandsId(id);
		verify(mockBuildRepo).findBySandsId(id);
	}

	@Test
	public void findByArtifactGobletId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByArtifactGobletId(id);
		verify(mockBuildRepo).findByGobletId(id);
	}

	@Test
	public void findByArtifactCircletId_Build()
	{
		int id = build.getCharacter().getId();
		buildService.findByArtifactCircletId(id);
		verify(mockBuildRepo).findByCircletId(id);
	}

}
