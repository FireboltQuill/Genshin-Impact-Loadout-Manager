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

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
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
public class ArtifactController
{
	private static Logger logger = LogManager.getLogger(GenshinImpactLoadoutManagerController.class);

	private BuildService buildService;
	private GenshinCharacterService genshinCharacterService;
	private ArtifactCircletService artifactCircletService;
	private ArtifactFeatherService artifactFeatherService;
	private ArtifactFlowerService artifactFlowerService;
	private ArtifactGobletService artifactGobletService;
	private ArtifactSandsService artifactSandsService;

	@Autowired
	public ArtifactController(BuildService buildService, GenshinCharacterService characterService,
			UsersService usersService, WeaponService weaponService, ArtifactCircletService artifactCircletService,
			ArtifactFeatherService artifactFeatherService, ArtifactFlowerService artifactFlowerService,
			ArtifactGobletService artifactGobletService, ArtifactSandsService artifactSandsService)
	{
		super();
		this.buildService = buildService;
		this.genshinCharacterService = characterService;
		this.artifactCircletService = artifactCircletService;
		this.artifactFeatherService = artifactFeatherService;
		this.artifactFlowerService = artifactFlowerService;
		this.artifactGobletService = artifactGobletService;
		this.artifactSandsService = artifactSandsService;
	}

	@GetMapping("/deleteFeather")
	public String getDeleteFeatherPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<ArtifactFeather> artifactFeathers = artifactFeatherService.getAll();

		model.addAttribute("artifactFeathers", artifactFeathers);

		return "deleteFeathers";
	}

	@GetMapping("/deleteFlower")
	public String getDeleteFlowerPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<ArtifactFlower> artifactFlowers = artifactFlowerService.getAll();

		model.addAttribute("artifactFlowers", artifactFlowers);

		return "deleteFlowers";
	}

	@GetMapping("/deleteSands")
	public String getDeleteSandsPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<ArtifactSands> artifactSandss = artifactSandsService.getAll();

		model.addAttribute("artifactSandss", artifactSandss);

		return "deleteSandss";
	}

	@GetMapping("/deleteGoblet")
	public String getDeleteGobletPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<ArtifactGoblet> artifactGoblet = artifactGobletService.getAll();

		model.addAttribute("artifactGoblet", artifactGoblet);

		return "deleteGoblets";
	}

	@GetMapping("/deleteCirclet")
	public String getDeleteCircletPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<ArtifactCirclet> artifactCirclet = artifactCircletService.getAll();

		model.addAttribute("artifactCirclet", artifactCirclet);

		return "deleteCirclets";
	}

	@GetMapping("/deleteFeather/{id}")
	public String deleteFeatherPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

		List<Build> builds = buildService.findByArtifactFeatherId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			builds.get(i).setFeather(artifactFeatherService.findById(-1));
			buildService.save(builds.get(i));
		}

		ArtifactFeather feather = artifactFeatherService.findById(id);
		artifactFeatherService.delete(feather.getId());

		logger.info("FEATHER " + feather.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/deleteFlower/{id}")
	public String deleteFlowerPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

		List<Build> builds = buildService.findByArtifactFlowerId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			builds.get(i).setFlower(artifactFlowerService.findById(-1));
			buildService.save(builds.get(i));
		}

		ArtifactFlower flower = artifactFlowerService.findById(id);
		artifactFlowerService.delete(flower.getId());

		logger.info("FLOWER " + flower.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/deleteSands/{id}")
	public String deleteSandsPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

		List<Build> builds = buildService.findByArtifactSandsId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			builds.get(i).setSands(artifactSandsService.findById(-1));
			buildService.save(builds.get(i));
		}

		ArtifactSands sands = artifactSandsService.findById(id);
		artifactSandsService.delete(sands.getId());

		logger.info("SANDS " + sands.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/deleteGoblet/{id}")
	public String deleteGobletPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

		List<Build> builds = buildService.findByArtifactGobletId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			builds.get(i).setGoblet(artifactGobletService.findById(-1));
			buildService.save(builds.get(i));
		}

		ArtifactGoblet goblet = artifactGobletService.findById(id);
		artifactGobletService.delete(goblet.getId());

		logger.info("GOBLET " + goblet.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/deleteCirclet/{id}")
	public String deleteCircletPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

		List<Build> builds = buildService.findByArtifactCircletId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			builds.get(i).setCirclet(artifactCircletService.findById(-1));
			buildService.save(builds.get(i));
		}

		ArtifactCirclet circlet = artifactCircletService.findById(id);
		artifactCircletService.delete(circlet.getId());

		logger.info("CIRCLET " + circlet.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/createFeather")
	public String createFeatherPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("feather", new ArtifactFeather("", "", 0, ""));

		return "createFeather";
	}

	@GetMapping("/createFlower")
	public String createFlowerPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("flower", new ArtifactFlower("", "", 0, ""));

		return "createFlower";
	}

	@GetMapping("/createSands")
	public String createSandsPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("sands", new ArtifactSands("", "", 0, ""));

		return "createSands";
	}

	@GetMapping("/createGoblet")
	public String createGobletPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("goblet", new ArtifactGoblet("", "", 0, ""));

		return "createGoblet";
	}

	@GetMapping("/createCirclet")
	public String createCircletPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("circlet", new ArtifactCirclet("", "", 0, ""));

		return "createCirclet";
	}

	@PostMapping("/createFeatherS")
	public String processCreateFeather(@ModelAttribute ArtifactFeather feather, Model model)
	{
		feather.setId(artifactFeatherService.getAll().size());
		artifactFeatherService.save(feather);

		logger.info("FEATHER " + feather.getName() + " CREATED!");

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@PostMapping("/createFlowerS")
	public String processCreateFlower(@ModelAttribute ArtifactFlower flower, Model model)
	{
		flower.setId(artifactFlowerService.getAll().size());
		artifactFlowerService.save(flower);

		logger.info("FLOWER " + flower.getName() + " CREATED!");

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@PostMapping("/createSandsS")
	public String processCreateSands(@ModelAttribute ArtifactSands sands, Model model)
	{
		sands.setId(artifactSandsService.getAll().size());
		artifactSandsService.save(sands);

		logger.info("SANDS " + sands.getName() + " CREATED!");

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@PostMapping("/createGobletS")
	public String processCreateGoblet(@ModelAttribute ArtifactGoblet goblet, Model model)
	{
		goblet.setId(artifactGobletService.getAll().size());
		artifactGobletService.save(goblet);

		logger.info("GOBLET " + goblet.getName() + " CREATED!");

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@PostMapping("/createCircletS")
	public String processCreateCirclet(@ModelAttribute ArtifactCirclet circlet, Model model)
	{
		circlet.setId(artifactCircletService.getAll().size());
		artifactCircletService.save(circlet);

		logger.info("CIRCLET " + circlet.getName() + " CREATED!");

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@GetMapping("/editFlowerPage/{id}")
	public String getFlowerBuildPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<ArtifactFlower> flowers = artifactFlowerService.getAll();
		Build build = buildService.findById(id);

		model.addAttribute("artifacts", flowers);
		model.addAttribute("build", build);
		model.addAttribute("artifactType", "flower");

		return "editArtifact";
	}

	@GetMapping("/editFeatherPage/{id}")
	public String getFeatherBuildPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<ArtifactFeather> feathers = artifactFeatherService.getAll();
		Build build = buildService.findById(id);

		model.addAttribute("artifacts", feathers);
		model.addAttribute("build", build);
		model.addAttribute("artifactType", "feather");

		return "editArtifact";
	}

	@GetMapping("/editSandsPage/{id}")
	public String getSandsBuildPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<ArtifactSands> sands = artifactSandsService.getAll();
		Build build = buildService.findById(id);

		model.addAttribute("artifacts", sands);
		model.addAttribute("build", build);
		model.addAttribute("artifactType", "sands");

		return "editArtifact";
	}

	@GetMapping("/editGobletPage/{id}")
	public String getGobletBuildPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<ArtifactGoblet> goblets = artifactGobletService.getAll();
		Build build = buildService.findById(id);

		model.addAttribute("artifacts", goblets);
		model.addAttribute("build", build);
		model.addAttribute("artifactType", "goblet");

		return "editArtifact";
	}

	@GetMapping("/editCircletPage/{id}")
	public String getCircletBuildPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<ArtifactCirclet> circlets = artifactCircletService.getAll();
		Build build = buildService.findById(id);

		model.addAttribute("artifacts", circlets);
		model.addAttribute("build", build);
		model.addAttribute("artifactType", "circlet");

		return "editArtifact";
	}

	@PostMapping("/editBuildArtifact/{id}/{id2}/{artifactType}")
	public String editBuildArtifact(HttpSession session, @PathVariable int id, @PathVariable int id2,
			@PathVariable String artifactType)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		Build buildT = buildService.findById(id2);

		if (artifactType.equals("flower"))
		{
			ArtifactFlower flowerT = artifactFlowerService.findById(id);
			buildT.setFlower(flowerT);

			buildService.save(buildT);

			logger.info(buildT.getCharacter().getName() + " FLOWER CHANGED TO " + flowerT.getName());
		}
		else if (artifactType.equals("feather"))
		{
			ArtifactFeather featherT = artifactFeatherService.findById(id);
			buildT.setFeather(featherT);

			buildService.save(buildT);

			logger.info(buildT.getCharacter().getName() + " FEATHER CHANGED TO " + featherT.getName());
		}
		else if (artifactType.equals("sands"))
		{
			ArtifactSands sandsT = artifactSandsService.findById(id);
			buildT.setSands(sandsT);

			buildService.save(buildT);

			logger.info(buildT.getCharacter().getName() + " SANDS CHANGED TO " + sandsT.getName());

		}
		else if (artifactType.equals("goblet"))
		{
			ArtifactGoblet gobletT = artifactGobletService.findById(id);
			buildT.setGoblet(gobletT);

			buildService.save(buildT);

			logger.info(buildT.getCharacter().getName() + " GOBLET CHANGED TO " + gobletT.getName());
		}
		else if (artifactType.equals("circlet"))
		{

			ArtifactCirclet circletT = artifactCircletService.findById(id);
			buildT.setCirclet(circletT);

			buildService.save(buildT);

			logger.info(buildT.getCharacter().getName() + " CIRCLET CHANGED TO " + circletT.getName());
		}

		int redirectBuild = buildT.getCharacter().getId();

		return "redirect:/viewBuild/" + redirectBuild;
	}
}
