package com.fdmgroup.GenshinImpactLoadoutManager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Weapon;
import com.fdmgroup.GenshinImpactLoadoutManager.service.ArtifactCircletService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.ArtifactFeatherService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.ArtifactFlowerService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.ArtifactGobletService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.ArtifactSandsService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.BuildService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.GenshinCharacterService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.UsersService;
import com.fdmgroup.GenshinImpactLoadoutManager.service.WeaponService;

@Controller
public class WeaponController
{
	private static Logger logger = LogManager.getLogger(GenshinImpactLoadoutManagerController.class);

	private BuildService buildService;
	private GenshinCharacterService genshinCharacterService;
	private WeaponService weaponService;

	@Autowired
	public WeaponController(BuildService buildService, GenshinCharacterService characterService,
			UsersService usersService, WeaponService weaponService, ArtifactCircletService artifactCircletService,
			ArtifactFeatherService artifactFeatherService, ArtifactFlowerService artifactFlowerService,
			ArtifactGobletService artifactGobletService, ArtifactSandsService artifactSandsService)
	{
		super();
		this.buildService = buildService;
		this.genshinCharacterService = characterService;
		this.weaponService = weaponService;
	}

	@GetMapping("/deleteWeapon")
	public String getDeleteWeaponPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<Weapon> weapons = weaponService.getAll();

		model.addAttribute("weapons", weapons);

		return "deleteWeapons";
	}

	@GetMapping("/deleteWeapon/{id}")
	public String deleteWeaponPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

		List<Build> builds = buildService.findByWeaponId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			builds.get(i).setWeapon(weaponService.findById(-1));
			buildService.save(builds.get(i));
		}

		Weapon weapon = weaponService.findById(id);
		weaponService.delete(weapon.getId());

		logger.info("WEAPON " + weapon.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/createWeapon")
	public String createWeaponPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("weapon", new Weapon());

		return "createWeapon";
	}

	@PostMapping("/createWeaponS")
	public String processCreateWeapon(@ModelAttribute Weapon weapon, Model model)
	{
		weapon.setId(weaponService.getAll().size());
		weaponService.save(weapon);

		logger.info("WEAPON " + weapon.getName() + " CREATED!");

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@GetMapping("/editWeaponPage/{id}")
	public String getEditWeaponsPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		Build build = buildService.findById(id);
		List<Weapon> weapons = weaponService.findByWeaponType(build.getCharacter().getWeaponType());
		weapons.add(weaponService.findById(-1));

		model.addAttribute("weapons", weapons);
		model.addAttribute("build", build);
		return "editWeapon";
	}

	@PostMapping("/editBuildWeapon/{id}/{id2}")
	public String editBuildWeapon(HttpSession session, @PathVariable int id, @PathVariable int id2)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		Weapon weaponT = weaponService.findById(id);
		Build buildT = buildService.findById(id2);
		buildT.setWeapon(weaponT);

		buildService.save(buildT);

		logger.info(buildT.getCharacter().getName() + " WEAPON CHANGED TO " + weaponT.getName());

		int redirectBuild = buildT.getCharacter().getId();

		return "redirect:/viewBuild/" + redirectBuild;

	}
}
