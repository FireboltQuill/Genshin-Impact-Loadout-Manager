package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Weapon;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.WeaponRepository;

@SpringBootTest
public class WeaponServiceTest
{
	@Autowired
	private WeaponService weaponService;
	
	@MockBean
	private WeaponRepository mockWeaponRepo;
	
	@Test
	public void save_Weapon()
	{
		Weapon weapon = new Weapon();
		weaponService.save(weapon);
		verify(mockWeaponRepo).save(weapon);
	}
	
	@Test
	public void delete_Weapon()
	{
		Weapon weapon = new Weapon();
		int id = weapon.getId();
		weaponService.delete(id);
		verify(mockWeaponRepo).deleteById(id);
	}
	
	@Test
	public void getAll_Weapons()
	{
		weaponService.getAll();
		verify(mockWeaponRepo).findAll();
	}

	@Test
	public void findById_Weapon()
	{
		Weapon weapon = new Weapon();
		int id = weapon.getId();
		weaponService.findById(id);
		verify(mockWeaponRepo).findById(id);
	}
	
	@Test
	public void findByWeaponType_Weapon()
	{
		Weapon weapon = new Weapon();
		String type = weapon.getWeaponType();
		weaponService.findByWeaponType(type);
		verify(mockWeaponRepo).findByWeaponType(type);
	}

}
