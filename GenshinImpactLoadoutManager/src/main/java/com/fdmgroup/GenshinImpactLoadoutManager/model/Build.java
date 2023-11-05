package com.fdmgroup.GenshinImpactLoadoutManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CHARACTER_BUILDS")
public class Build
{

	@Id
	private int id;

	@OneToOne
	@JoinColumn(name = "character_id", referencedColumnName = "id")
	private GenshinCharacter character;

	@OneToOne
	@JoinColumn(name = "users_id", referencedColumnName = "id")
	private Users users;

	@OneToOne
	@JoinColumn(name = "artifact_flower_id", referencedColumnName = "id")
	private ArtifactFlower flower;

	@OneToOne
	@JoinColumn(name = "artifact_feather_id", referencedColumnName = "id")
	private ArtifactFeather feather;

	@OneToOne
	@JoinColumn(name = "artifact_sands_id", referencedColumnName = "id")
	private ArtifactSands sands;

	@OneToOne
	@JoinColumn(name = "artifact_goblet_id", referencedColumnName = "id")
	private ArtifactGoblet goblet;

	@OneToOne
	@JoinColumn(name = "artifact_circlet_id", referencedColumnName = "id")
	private ArtifactCirclet circlet;

	@OneToOne
	@JoinColumn(name = "weapon_id", referencedColumnName = "id")
	private Weapon weapon;

	public Build()
	{
		super();
	}

	public Build(GenshinCharacter character, Users users, ArtifactFlower flower, ArtifactFeather feather,
			ArtifactSands sands, ArtifactGoblet goblet, ArtifactCirclet circlet, Weapon weapon)
	{
		super();
		this.character = character;
		this.users = users;
		this.flower = flower;
		this.feather = feather;
		this.sands = sands;
		this.goblet = goblet;
		this.circlet = circlet;
		this.weapon = weapon;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public GenshinCharacter getCharacter()
	{
		return character;
	}

	public void setCharacter(GenshinCharacter character)
	{
		this.character = character;
	}

	public Users getUsers()
	{
		return users;
	}

	public void setUsers(Users users)
	{
		this.users = users;
	}

	public ArtifactFlower getFlower()
	{
		return flower;
	}

	public void setFlower(ArtifactFlower flower)
	{
		this.flower = flower;
	}

	public ArtifactFeather getFeather()
	{
		return feather;
	}

	public void setFeather(ArtifactFeather feather)
	{
		this.feather = feather;
	}

	public ArtifactSands getSands()
	{
		return sands;
	}

	public void setSands(ArtifactSands sands)
	{
		this.sands = sands;
	}

	public ArtifactGoblet getGoblet()
	{
		return goblet;
	}

	public void setGoblet(ArtifactGoblet goblet)
	{
		this.goblet = goblet;
	}

	public ArtifactCirclet getCirclet()
	{
		return circlet;
	}

	public void setCirclet(ArtifactCirclet circlet)
	{
		this.circlet = circlet;
	}

	public Weapon getWeapon()
	{
		return weapon;
	}

	public void setWeapon(Weapon weapon)
	{
		this.weapon = weapon;
	}

}