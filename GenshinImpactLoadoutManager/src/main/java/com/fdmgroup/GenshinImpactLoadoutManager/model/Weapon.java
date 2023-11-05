package com.fdmgroup.GenshinImpactLoadoutManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEAPONS")
public class Weapon
{

	@Id
	private int id;

	private String name;
	private String weaponType;
	private int mainStat;

	public Weapon()
	{
		super();
	}

	public Weapon(String name, String weaponType, int mainStat)
	{
		super();
		this.name = name;
		this.weaponType = weaponType;
		this.mainStat = mainStat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getWeaponType()
	{
		return weaponType;
	}

	public void setWeaponType(String weaponType)
	{
		this.weaponType = weaponType;
	}

	public int getMainStat()
	{
		return mainStat;
	}

	public void setMainStat(int mainStat)
	{
		this.mainStat = mainStat;
	}

}