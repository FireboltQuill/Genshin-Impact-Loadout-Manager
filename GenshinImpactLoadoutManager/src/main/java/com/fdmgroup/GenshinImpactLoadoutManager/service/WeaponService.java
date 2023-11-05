package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Weapon;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.WeaponRepository;

@Service
public class WeaponService
{
	
	private WeaponRepository WeaponRepo;

	@Autowired
	public WeaponService(WeaponRepository WeaponRepo)
	{
		super();
		this.WeaponRepo = WeaponRepo;
	}
	
	@Transactional
	public void save(Weapon Weapon)
	{
		WeaponRepo.save(Weapon);
	}
	
	@Transactional
	public void delete(int id)
	{
		WeaponRepo.deleteById(id);
	}
	
	public List<Weapon> getAll()
	{
		return WeaponRepo.findAll();
	}
	
	public Weapon findById(int id)
	{
		return WeaponRepo.findById(id).orElse(null);
	}
	
	public List<Weapon> findByWeaponType(String type)
	{
		return WeaponRepo.findByWeaponType(type);
	}

}
