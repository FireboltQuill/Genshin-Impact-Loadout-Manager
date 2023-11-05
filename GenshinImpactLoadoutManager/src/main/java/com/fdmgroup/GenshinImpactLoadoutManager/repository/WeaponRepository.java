package com.fdmgroup.GenshinImpactLoadoutManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Weapon;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Integer>
{
	List<Weapon> findByName(String name);
	
	List<Weapon> findByWeaponType(String type);
}
