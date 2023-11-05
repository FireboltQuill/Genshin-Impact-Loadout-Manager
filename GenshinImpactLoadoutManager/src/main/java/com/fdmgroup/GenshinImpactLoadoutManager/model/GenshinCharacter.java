package com.fdmgroup.GenshinImpactLoadoutManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHARACTERS")
public class GenshinCharacter
{
	@Id
	private int id;

	private String name;
	private String element;
	private String weaponType;

	private double health;
	private double attack;
	private double defense;
	private double elementalMastery;

	private double critRate;
	private double critDamage;
	private double healingBonus;
	private double energyRecharge;

	private double anemoDamage;
	private double cryoDamage;
	private double electroDamage;
	private double geoDamage;
	private double hydroDamage;
	private double pyroDamage;
	private double physicalDamage;

	public GenshinCharacter()
	{
		super();
	}

	public GenshinCharacter(String name, String element, String weaponType, double health, double attack, double defense,
			double elementalMastery, double critRate, double critDamage, double healingBonus, double energyRecharge,
			double anemoDamage, double cryoDamage, double electroDamage, double geoDamage, double hydroDamage,
			double pyroDamage, double physicalDamage)
	{
		super();
		this.name = name;
		this.element = element;
		this.weaponType = weaponType;
		this.health = health;
		this.attack = attack;
		this.defense = defense;
		this.elementalMastery = elementalMastery;
		this.critRate = critRate;
		this.critDamage = critDamage;
		this.healingBonus = healingBonus;
		this.energyRecharge = energyRecharge;
		this.anemoDamage = anemoDamage;
		this.cryoDamage = cryoDamage;
		this.electroDamage = electroDamage;
		this.geoDamage = geoDamage;
		this.hydroDamage = hydroDamage;
		this.pyroDamage = pyroDamage;
		this.physicalDamage = physicalDamage;
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

	public String getElement()
	{
		return element;
	}

	public void setElement(String element)
	{
		this.element = element;
	}

	public String getWeaponType()
	{
		return weaponType;
	}

	public void setWeaponType(String weaponType)
	{
		this.weaponType = weaponType;
	}

	public double getHealth()
	{
		return health;
	}

	public void setHealth(double health)
	{
		this.health = health;
	}

	public double getAttack()
	{
		return attack;
	}

	public void setAttack(double attack)
	{
		this.attack = attack;
	}

	public double getDefense()
	{
		return defense;
	}

	public void setDefense(double defense)
	{
		this.defense = defense;
	}

	public double getElementalMastery()
	{
		return elementalMastery;
	}

	public void setElementalMastery(double elementalMastery)
	{
		this.elementalMastery = elementalMastery;
	}

	public double getCritRate()
	{
		return critRate;
	}

	public void setCritRate(double critRate)
	{
		this.critRate = critRate;
	}

	public double getCritDamage()
	{
		return critDamage;
	}

	public void setCritDamage(double critDamage)
	{
		this.critDamage = critDamage;
	}

	public double getHealingBonus()
	{
		return healingBonus;
	}

	public void setHealingBonus(double healingBonus)
	{
		this.healingBonus = healingBonus;
	}

	public double getEnergyRecharge()
	{
		return energyRecharge;
	}

	public void setEnergyRecharge(double energyRecharge)
	{
		this.energyRecharge = energyRecharge;
	}

	public double getAnemoDamage()
	{
		return anemoDamage;
	}

	public void setAnemoDamage(double anemoDamage)
	{
		this.anemoDamage = anemoDamage;
	}

	public double getCryoDamage()
	{
		return cryoDamage;
	}

	public void setCryoDamage(double cryoDamage)
	{
		this.cryoDamage = cryoDamage;
	}

	public double getElectroDamage()
	{
		return electroDamage;
	}

	public void setElectroDamage(double electroDamage)
	{
		this.electroDamage = electroDamage;
	}

	public double getGeoDamage()
	{
		return geoDamage;
	}

	public void setGeoDamage(double geoDamage)
	{
		this.geoDamage = geoDamage;
	}

	public double getHydroDamage()
	{
		return hydroDamage;
	}

	public void setHydroDamage(double hydroDamage)
	{
		this.hydroDamage = hydroDamage;
	}

	public double getPyroDamage()
	{
		return pyroDamage;
	}

	public void setPyroDamage(double pyroDamage)
	{
		this.pyroDamage = pyroDamage;
	}

	public double getPhysicalDamage()
	{
		return physicalDamage;
	}

	public void setPhysicalDamage(double physicalDamage)
	{
		this.physicalDamage = physicalDamage;
	}

}