package com.fdmgroup.GenshinImpactLoadoutManager.config;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fdmgroup.GenshinImpactLoadoutManager.controller.Utils;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Users;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Weapon;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactCircletRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactFeatherRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactFlowerRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactGobletRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.ArtifactSandsRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.BuildRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.GenshinCharacterRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.UsersRepository;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.WeaponRepository;

@Component
public class TestingData
{
	private static Logger logger = LogManager.getLogger(TestingData.class);

	boolean alreadySetup = false;

	private UsersRepository usersRepository;

	private BuildRepository buildRepository;

	private GenshinCharacterRepository genshinCharacterRepository;

	private WeaponRepository weaponRepository;

	private ArtifactFlowerRepository artifactFlowerRepository;

	private ArtifactFeatherRepository artifactFeatherRepository;

	private ArtifactSandsRepository artifactSandsRepository;

	private ArtifactGobletRepository artifactGobletRepository;

	private ArtifactCircletRepository artifactCircletRepository;

	public TestingData(UsersRepository usersRepository, BuildRepository buildRepository,
			GenshinCharacterRepository genshinCharacterRepository, WeaponRepository weaponRepository,
			ArtifactFlowerRepository artifactFlowerRepository, ArtifactFeatherRepository artifactFeatherRepository,
			ArtifactSandsRepository artifactSandsRepository, ArtifactGobletRepository artifactGobletRepository,
			ArtifactCircletRepository artifactCircletRepository)
	{
		super();
		this.usersRepository = usersRepository;
		this.buildRepository = buildRepository;
		this.genshinCharacterRepository = genshinCharacterRepository;
		this.weaponRepository = weaponRepository;
		this.artifactFlowerRepository = artifactFlowerRepository;
		this.artifactFeatherRepository = artifactFeatherRepository;
		this.artifactSandsRepository = artifactSandsRepository;
		this.artifactGobletRepository = artifactGobletRepository;
		this.artifactCircletRepository = artifactCircletRepository;
	}

	public void startUp()
	{
		if (alreadySetup)
		{
			logger.info("DATA ALREADY EXIST");
			return;
		}

		if (usersRepository.findAll().size() > 0)
		{
			logger.info("DATA ALREADY EXIST");
			alreadySetup = true;
			return;
		}

		logger.info("ADDING DATA");

		// Users
		Users user1 = null, user2 = null, user3 = null;
		try
		{
			user1 = new Users("FireboltQuill", Utils.toHexString(Utils.getSHA("fquill")), "admin");
			user1.setId(0);
			usersRepository.save(user1);
			user2 = new Users("Akimei", Utils.toHexString(Utils.getSHA("mei")), "user");
			user2.setId(1);
			usersRepository.save(user2);
			user3 = new Users("Izayumi", Utils.toHexString(Utils.getSHA("yumi")), "user");
			user3.setId(2);
			usersRepository.save(user3);
			logger.info("USERS ADDED");
		} catch (NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		}

		// Characters
		GenshinCharacter character1 = new GenshinCharacter("Traveller", "adaptive", "sword", 10875, 262.88, 683, 0, 5,
				50, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		character1.setId(0);
		genshinCharacterRepository.save(character1);

		GenshinCharacter character2 = new GenshinCharacter("Amber", "pyro", "bow", 9461, 276.52, 601, 0, 5, 50, 0, 0, 0,
				0, 0, 0, 0, 0, 0);
		character2.setId(1);
		genshinCharacterRepository.save(character2);

		GenshinCharacter character3 = new GenshinCharacter("Barbara", "hydro", "catalyst", 12135.88, 159, 669, 0, 5, 50,
				0, 0, 0, 0, 0, 0, 0, 0, 0);
		character3.setId(2);
		genshinCharacterRepository.save(character3);

		GenshinCharacter character4 = new GenshinCharacter("Beidou", "electro", "claymore", 13050, 225, 648, 0, 5, 50,
				0, 0, 0, 0, 24, 0, 0, 0, 0);
		character4.setId(3);
		genshinCharacterRepository.save(character4);

		GenshinCharacter character5 = new GenshinCharacter("Bennett", "pyro", "sword", 12397, 191, 771, 0, 5, 50, 0,
				26.7, 0, 0, 0, 0, 0, 0, 0);
		character5.setId(4);
		genshinCharacterRepository.save(character5);
		
		logger.info("CHARACTES ADDED");

		// Weapons
		Weapon weapon1 = new Weapon("EMPTY", "WEAPON", 0);
		weapon1.setId(-1);
		weaponRepository.save(weapon1);

		Weapon weapon2 = new Weapon("Primordial Jade Cutter", "sword", 542);
		weapon2.setId(1);
		weaponRepository.save(weapon2);

		Weapon weapon3 = new Weapon("Aquila Favonia", "sword", 674);
		weapon3.setId(2);
		weaponRepository.save(weapon3);

		Weapon weapon4 = new Weapon("Elegy of the End", "bow", 608);
		weapon4.setId(3);
		weaponRepository.save(weapon4);

		Weapon weapon5 = new Weapon("Everlasting Moonglow", "catalyst", 608);
		weapon5.setId(4);
		weaponRepository.save(weapon5);

		Weapon weapon6 = new Weapon("Wolfs Gravestone", "claymore", 608);
		weapon6.setId(5);
		weaponRepository.save(weapon6);

		Weapon weapon7 = new Weapon("Thundering Pulse", "bow", 608);
		weapon7.setId(6);
		weaponRepository.save(weapon7);

		Weapon weapon8 = new Weapon("Skyward Atlas", "catalyst", 674);
		weapon8.setId(7);
		weaponRepository.save(weapon8);

		Weapon weapon9 = new Weapon("Song of Broken Pines", "claymore", 741);
		weapon9.setId(8);
		weaponRepository.save(weapon9);
		
		logger.info("WEAPONS ADDED");

		// Artifacts

		// Flower
		ArtifactFlower artifactFlower1 = new ArtifactFlower("EMPTY", "EMPTY", 0, "EMPTY");
		artifactFlower1.setId(-1);
		artifactFlowerRepository.save(artifactFlower1);

		ArtifactFlower artifactFlower2 = new ArtifactFlower("Witchs Flower of Blaze", "health", 4780,
				"Crimson Witch of Flames");
		artifactFlower2.setId(1);
		artifactFlowerRepository.save(artifactFlower2);

		ArtifactFlower artifactFlower3 = new ArtifactFlower("Royal Flora", "health", 4780, "Noblesse Oblige");
		artifactFlower3.setId(2);
		artifactFlowerRepository.save(artifactFlower3);

		ArtifactFlower artifactFlower4 = new ArtifactFlower("Thunderbirds Mercy", "health", 4780, "Thundering Fury");
		artifactFlower4.setId(3);
		artifactFlowerRepository.save(artifactFlower4);

		ArtifactFlower artifactFlower5 = new ArtifactFlower("Maidens Distant Love", "health", 4780, "Maiden Beloved");
		artifactFlower5.setId(4);
		artifactFlowerRepository.save(artifactFlower5);

		ArtifactFlower artifactFlower6 = new ArtifactFlower("Gladiators Nostalgia", "health", 4780,
				"Gladiators Finale");
		artifactFlower6.setId(5);
		artifactFlowerRepository.save(artifactFlower6);
		
		logger.info("FLOWERS ADDED");

		// Feather
		ArtifactFeather artifactFeather1 = new ArtifactFeather("EMPTY", "EMPTY", 0, "EMPTY");
		artifactFeather1.setId(-1);
		artifactFeatherRepository.save(artifactFeather1);

		ArtifactFeather artifactFeather2 = new ArtifactFeather("Witchs Ever-Burning Plume", "attack", 311,
				"Crimson Witch of Flames");
		artifactFeather2.setId(1);
		artifactFeatherRepository.save(artifactFeather2);

		ArtifactFeather artifactFeather3 = new ArtifactFeather("Royal Plume", "attack", 311, "Noblesse Oblige");
		artifactFeather3.setId(2);
		artifactFeatherRepository.save(artifactFeather3);

		ArtifactFeather artifactFeather4 = new ArtifactFeather("Survivor of Catastrophe", "attack", 311,
				"Thundering Fury");
		artifactFeather4.setId(3);
		artifactFeatherRepository.save(artifactFeather4);

		ArtifactFeather artifactFeather5 = new ArtifactFeather("Maidens Heart-Stricken Infatuation", "attack", 311,
				"Maiden Beloved");
		artifactFeather5.setId(4);
		artifactFeatherRepository.save(artifactFeather5);

		ArtifactFeather artifactFeather6 = new ArtifactFeather("Gladiators Destiny", "attack", 311,
				"Gladiators Finale");
		artifactFeather6.setId(5);
		artifactFeatherRepository.save(artifactFeather6);
		
		logger.info("FEATHERS ADDED");

		// Sands
		ArtifactSands artifactSands1 = new ArtifactSands("EMPTY", "EMPTY", 0, "EMPTY");
		artifactSands1.setId(-1);
		artifactSandsRepository.save(artifactSands1);

		ArtifactSands artifactSands2 = new ArtifactSands("Witchs End Time", "attackP", 46.6, "Crimson Witch of Flames");
		artifactSands2.setId(1);
		artifactSandsRepository.save(artifactSands2);

		ArtifactSands artifactSands3 = new ArtifactSands("Royal Pocket Watch", "energy_recharge", 51.8,
				"Noblesse Oblige");
		artifactSands3.setId(2);
		artifactSandsRepository.save(artifactSands3);

		ArtifactSands artifactSands4 = new ArtifactSands("Hourglass of Thunder", "attackP", 46.6, "Thundering Fury");
		artifactSands4.setId(3);
		artifactSandsRepository.save(artifactSands4);

		ArtifactSands artifactSands5 = new ArtifactSands("Maidens Passing Youth", "hpP", 46.6, "Maiden Beloved");
		artifactSands5.setId(4);
		artifactSandsRepository.save(artifactSands5);

		ArtifactSands artifactSands6 = new ArtifactSands("Gladiators Longing", "attackP", 46.6, "Gladiators Finale");
		artifactSands6.setId(5);
		artifactSandsRepository.save(artifactSands6);
		
		logger.info("SANDS' ADDED");

		// Goblet
		ArtifactGoblet artifactGoblet1 = new ArtifactGoblet("EMPTY", "EMPTY", 0, "EMPTY");
		artifactGoblet1.setId(-1);
		artifactGobletRepository.save(artifactGoblet1);

		ArtifactGoblet artifactGoblet2 = new ArtifactGoblet("Witchs Heart Flames", "pyro_damage", 46.6,
				"Crimson Witch of Flames");
		artifactGoblet2.setId(1);
		artifactGobletRepository.save(artifactGoblet2);

		ArtifactGoblet artifactGoblet3 = new ArtifactGoblet("Royal Silver Urn", "pyro_damage", 46.6, "Noblesse Oblige");
		artifactGoblet3.setId(2);
		artifactGobletRepository.save(artifactGoblet3);

		ArtifactGoblet artifactGoblet4 = new ArtifactGoblet("Omen of Thunderstorm", "electro_damage", 46.6,
				"Thundering Fury");
		artifactGoblet4.setId(3);
		artifactGobletRepository.save(artifactGoblet4);

		ArtifactGoblet artifactGoblet5 = new ArtifactGoblet("Maidens Fleeting Leisure", "hpP", 46.6, "Maiden Beloved");
		artifactGoblet5.setId(4);
		artifactGobletRepository.save(artifactGoblet5);

		ArtifactGoblet artifactGoblet6 = new ArtifactGoblet("Gladiators Intoxication", "physical_damage", 58.3,
				"Gladiators Finale");
		artifactGoblet6.setId(5);
		artifactGobletRepository.save(artifactGoblet6);
		
		logger.info("GOBLETSADDED");

		// Circlet
		ArtifactCirclet artifactCirclet1 = new ArtifactCirclet("EMPTY", "EMPTY", 0, "EMPTY");
		artifactCirclet1.setId(-1);
		artifactCircletRepository.save(artifactCirclet1);

		ArtifactCirclet artifactCirclet2 = new ArtifactCirclet("Witchs Scorching Hat", "crit_damage", 62.2,
				"Crimson Witch of Flames");
		artifactCirclet2.setId(1);
		artifactCircletRepository.save(artifactCirclet2);

		ArtifactCirclet artifactCirclet3 = new ArtifactCirclet("Royal Masque", "healing_bonus", 35.9,
				"Noblesse Oblige");
		artifactCirclet3.setId(2);
		artifactCircletRepository.save(artifactCirclet3);

		ArtifactCirclet artifactCirclet4 = new ArtifactCirclet("Thunder Summoners Crown", "crit_damage", 62.2,
				"Thundering Fury");
		artifactCirclet4.setId(3);
		artifactCircletRepository.save(artifactCirclet4);

		ArtifactCirclet artifactCirclet5 = new ArtifactCirclet("Maidens Fading Beauty", "healing_bonus", 35.9,
				"Maiden Beloved");
		artifactCirclet5.setId(4);
		artifactCircletRepository.save(artifactCirclet5);

		ArtifactCirclet artifactCirclet6 = new ArtifactCirclet("Gladiators Triumphus", "crit_damage", 62.2,
				"Gladiators Finale");
		artifactCirclet6.setId(5);
		artifactCircletRepository.save(artifactCirclet6);
		
		logger.info("CIRCLETS ADDED");

		// Builds

		// Character 1
		Build build1 = new Build(character1, user1, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build1.setId(0);
		buildRepository.save(build1);

		Build build2 = new Build(character1, user2, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build2.setId(1);
		buildRepository.save(build2);

		Build build3 = new Build(character1, user3, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build3.setId(2);
		buildRepository.save(build3);

		// Character 2
		Build build4 = new Build(character2, user1, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build4.setId(3);
		buildRepository.save(build4);

		Build build5 = new Build(character2, user2, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build5.setId(4);
		buildRepository.save(build5);

		Build build6 = new Build(character2, user3, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build6.setId(5);
		buildRepository.save(build6);

		// Character 3
		Build build7 = new Build(character3, user1, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build7.setId(6);
		buildRepository.save(build7);

		Build build8 = new Build(character3, user2, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build8.setId(7);
		buildRepository.save(build8);

		Build build9 = new Build(character3, user3, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build9.setId(8);
		buildRepository.save(build9);

		// Character 4
		Build build10 = new Build(character4, user1, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build10.setId(9);
		buildRepository.save(build10);

		Build build11 = new Build(character4, user2, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build11.setId(10);
		buildRepository.save(build11);

		Build build12 = new Build(character4, user3, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build12.setId(11);
		buildRepository.save(build12);

		// Character 5
		Build build13 = new Build(character5, user1, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build13.setId(12);
		buildRepository.save(build13);

		Build build14 = new Build(character5, user2, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build14.setId(13);
		buildRepository.save(build14);

		Build build15 = new Build(character5, user3, artifactFlower1, artifactFeather1, artifactSands1, artifactGoblet1,
				artifactCirclet1, weapon1);
		build15.setId(14);
		buildRepository.save(build15);
		
		logger.info("BUILDS ADDED");

		alreadySetup = true;
	}

	public boolean isAlreadySetup()
	{
		return alreadySetup;
	}

	public void setAlreadySetup(boolean alreadySetup)
	{
		this.alreadySetup = alreadySetup;
	}
}
