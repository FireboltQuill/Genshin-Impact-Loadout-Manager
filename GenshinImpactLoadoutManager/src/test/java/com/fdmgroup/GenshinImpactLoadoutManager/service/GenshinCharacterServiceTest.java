package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.GenshinCharacterRepository;

@SpringBootTest
public class GenshinCharacterServiceTest
{
	@Autowired
	private GenshinCharacterService genshinCharacterService;
	
	@MockBean
	private GenshinCharacterRepository mockGenshinCharacterRepo;
	
	@Test
	public void save_GenshinCharacter()
	{
		GenshinCharacter genshinCharacter = new GenshinCharacter();
		genshinCharacterService.save(genshinCharacter);
		verify(mockGenshinCharacterRepo).save(genshinCharacter);
	}
	
	@Test
	public void delete_GenshinCharacter()
	{
		GenshinCharacter genshinCharacter = new GenshinCharacter();
		int id = genshinCharacter.getId();
		genshinCharacterService.delete(id);
		verify(mockGenshinCharacterRepo).deleteById(id);
	}
	
	@Test
	public void getAll_GenshinCharacters()
	{
		genshinCharacterService.getAll();
		verify(mockGenshinCharacterRepo).findAll();
	}

	@Test
	public void findById_GenshinCharacter()
	{
		GenshinCharacter genshinCharacter = new GenshinCharacter();
		int id = genshinCharacter.getId();
		genshinCharacterService.findById(id);
		verify(mockGenshinCharacterRepo).findById(id);
	}

}
