package com.fdmgroup.GenshinImpactLoadoutManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ARTIFACT_FLOWER")
public class ArtifactFlower
{
	@Id
	private int id;

	private String name;
	private String mainType;
	private Double mainStat;
	private String artifactSet;

	public ArtifactFlower()
	{
		super();
	}

	public ArtifactFlower(String name, String mainType, double mainStat, String set)
	{
		super();
		this.name = name;
		this.mainType = mainType;
		this.mainStat = mainStat;
		this.artifactSet = set;
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

	public String getMainType()
	{
		return mainType;
	}

	public void setMainType(String mainType)
	{
		this.mainType = mainType;
	}

	public double getMainStat()
	{
		return mainStat;
	}

	public void setMainStat(double mainStat)
	{
		this.mainStat = mainStat;
	}

	public String getSet()
	{
		return artifactSet;
	}

	public void setSet(String set)
	{
		this.artifactSet = set;
	}
}