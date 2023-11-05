package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.GenshinCharacterRepository;

@Service
public class GenshinCharacterService
{
	
	private GenshinCharacterRepository CharacterRepo;

	@Autowired
	public GenshinCharacterService(GenshinCharacterRepository CharacterRepo)
	{
		super();
		this.CharacterRepo = CharacterRepo;
	}
	
	@Transactional
	public void save(GenshinCharacter Character)
	{
		CharacterRepo.save(Character);
	}
	
	@Transactional
	public void delete(int id)
	{
		CharacterRepo.deleteById(id);
	}
	
	public List<GenshinCharacter> getAll()
	{
		return CharacterRepo.findAll();
	}
	
	public GenshinCharacter findById(int id)
	{
		return CharacterRepo.findById(id).orElse(null);
	}

}
