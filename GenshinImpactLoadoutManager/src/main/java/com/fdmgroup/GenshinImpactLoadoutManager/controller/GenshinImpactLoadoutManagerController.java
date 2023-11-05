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

import com.fdmgroup.GenshinImpactLoadoutManager.config.TestingData;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Users;
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
public class GenshinImpactLoadoutManagerController
{
	private static Logger logger = LogManager.getLogger(GenshinImpactLoadoutManagerController.class);

	private BuildService buildService;
	private GenshinCharacterService genshinCharacterService;
	private UsersService usersService;
	private WeaponService weaponService;

	private ArtifactCircletService artifactCircletService;
	private ArtifactFeatherService artifactFeatherService;
	private ArtifactFlowerService artifactFlowerService;
	private ArtifactGobletService artifactGobletService;
	private ArtifactSandsService artifactSandsService;

	private TestingData td;

	@Autowired
	public GenshinImpactLoadoutManagerController(BuildService buildService,
			GenshinCharacterService genshinCharacterService, UsersService usersService, WeaponService weaponService,
			ArtifactCircletService artifactCircletService, ArtifactFeatherService artifactFeatherService,
			ArtifactFlowerService artifactFlowerService, ArtifactGobletService artifactGobletService,
			ArtifactSandsService artifactSandsService, TestingData td)
	{
		super();
		this.buildService = buildService;
		this.genshinCharacterService = genshinCharacterService;
		this.usersService = usersService;
		this.weaponService = weaponService;
		this.artifactCircletService = artifactCircletService;
		this.artifactFeatherService = artifactFeatherService;
		this.artifactFlowerService = artifactFlowerService;
		this.artifactGobletService = artifactGobletService;
		this.artifactSandsService = artifactSandsService;
		this.td = td;
	}

	@GetMapping("/")
	public String homePage(HttpSession session)
	{
		if (!td.isAlreadySetup())
		{
			td.startUp();
			session.invalidate();

			logger.info("RESET USERS");
			td.setAlreadySetup(true);
		}
		
		return "index";
	}

	@GetMapping("/viewCharacters")
	public String getCharactersPage(Model model)
	{
		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);
		logger.info("Viewing all Characters");
		return "allCharacters";
	}

	@GetMapping("/createPage")
	public String getCreatePage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
		{
			logger.warn("UNAUTHORISED ACCESS TO CONTROL PANEL");
			return "loginErrorUnauth";
		}

		return "createPage";
	}

	@GetMapping("/deleteCharacter")
	public String getDeleteCharacterPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();

		model.addAttribute("genshinCharacters", genshinCharacters);

		return "deleteCharacters";
	}

	@GetMapping("/deleteGenshinCharacter/{id}")
	public String deleteCharacterPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<Build> builds = buildService.findByCharacterId(id);
		for (int i = 0; i < builds.size(); i++)
		{
			buildService.delete(builds.get(i).getId());
		}

		GenshinCharacter genshinCharacter = genshinCharacterService.findById(id);

		genshinCharacterService.delete(genshinCharacter.getId());

		logger.info("CHARACTER " + genshinCharacter.getName() + " DELETED!");

		return "redirect:/createPage";
	}

	@GetMapping("/createCharacter")
	public String createCharacterPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		model.addAttribute("genshinCharacter", new GenshinCharacter());

		return "createCharacter";
	}

	@PostMapping("/createCharacterS")
	public String processCreateCharacter(@ModelAttribute GenshinCharacter genshinCharacter, Model model)
	{
		genshinCharacter.setId(genshinCharacterService.getAll().size());
		genshinCharacterService.save(genshinCharacter);

		logger.info("CHARACTER " + genshinCharacter.getName() + " CREATED!");

		List<Users> usersList = usersService.getAll();
		List<Build> buildList = buildService.getAll();

		for (int i = 0; i < usersList.size(); i++)
		{
			Build temp = new Build();

			temp.setId(buildList.size() + i);

			temp.setUsers(usersService.findById(usersList.get(i).getId()));
			temp.setCharacter(genshinCharacter);
			temp.setWeapon(weaponService.findById(-1));
			temp.setFeather(artifactFeatherService.findById(-1));
			temp.setFlower(artifactFlowerService.findById(-1));
			temp.setSands(artifactSandsService.findById(-1));
			temp.setGoblet(artifactGobletService.findById(-1));
			temp.setCirclet(artifactCircletService.findById(-1));

			buildService.save(temp);
			
			logger.info("CHARACTER: \"" + genshinCharacter.getName() + "\" BASE FOR USER: \"" + usersService.findById(usersList.get(i).getId()).getUsername() + "\" ADDED");
		}

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		model.addAttribute("genshinCharacters", genshinCharacters);

		return "allCharacters";
	}

	@GetMapping("/genshinCharacter/{id}")
	public String getCharacterPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<Build> builds = buildService.findByUsersId((int) (session.getAttribute("id")));

		Build build = new Build();

		for (int i = 0; i < builds.size(); i++)
		{
			if (builds.get(i).getCharacter().getId() == id)
			{
				build = builds.get(i);
			}
		}
		GenshinCharacter genshinCharacter = build.getCharacter();

		if (genshinCharacter != null)
		{
			model.addAttribute("genshinCharacter", genshinCharacter);

			double healthStat = genshinCharacter.getHealth() + build.getFlower().getMainStat();
			double attackStat = genshinCharacter.getAttack() + build.getWeapon().getMainStat()
					+ build.getFeather().getMainStat();
			double defenseStat = genshinCharacter.getDefense();
			double emStat = genshinCharacter.getElementalMastery();
			double critRateStat = genshinCharacter.getCritRate();
			double critDamageStat = genshinCharacter.getCritDamage();
			double healingBonusStat = genshinCharacter.getHealingBonus();
			double erStat = genshinCharacter.getEnergyRecharge();
			double anemoDamageStat = genshinCharacter.getAnemoDamage();
			double cryoDamageStat = genshinCharacter.getCryoDamage();
			double electroDamageStat = genshinCharacter.getElectroDamage();
			double geoDamageStat = genshinCharacter.getGeoDamage();
			double hydroDamageStat = genshinCharacter.getHydroDamage();
			double pyroDamageStat = genshinCharacter.getPyroDamage();
			double physicalDamageStat = genshinCharacter.getPhysicalDamage();

			double mainStat = build.getSands().getMainStat();
			String mainType = build.getSands().getMainType();

			if ("hpP".equals(mainType))
			{
				healthStat = Math.round(healthStat * ((mainStat / 100) + 1));
			}
			else if ("attackP".equals(mainType))
			{
				attackStat = Math.round(attackStat * ((mainStat / 100) + 1));
			}
			else if ("defenseP".equals(mainType))
			{
				defenseStat = Math.round(defenseStat * ((mainStat / 100) + 1));
			}
			else if ("elemental_mastery".equals(mainType))
			{
				emStat += mainStat;
			}
			else if ("energy_recharge".equals(mainType))
			{
				erStat += erStat;
			}

			mainStat = build.getGoblet().getMainStat();
			mainType = build.getGoblet().getMainType();
			if ("hpP".equals(mainType))
			{
				healthStat = Math.round(healthStat * ((mainStat / 100) + 1));
			}
			else if ("attackP".equals(mainType))
			{
				attackStat = Math.round(attackStat * ((mainStat / 100) + 1));
			}
			else if ("defenseP".equals(mainType))
			{
				defenseStat = Math.round(defenseStat * ((mainStat / 100) + 1));
			}
			else if ("elemental_mastery".equals(mainType))
			{
				emStat += mainStat;
			}
			else if ("anemo_damage".equals(mainType))
			{
				anemoDamageStat += mainStat;
			}
			else if ("cryo_damage".equals(mainType))
			{
				cryoDamageStat += mainStat;
			}
			else if ("electro_damage".equals(mainType))
			{
				electroDamageStat += mainStat;
			}
			else if ("geo_damage".equals(mainType))
			{
				geoDamageStat += mainStat;
			}
			else if ("hydro_damage".equals(mainType))
			{
				hydroDamageStat += mainStat;
			}
			else if ("pyro_damage".equals(mainType))
			{
				pyroDamageStat += mainStat;
			}
			else if ("physical_damage".equals(mainType))
			{
				physicalDamageStat += mainStat;
			}

			mainStat = build.getCirclet().getMainStat();
			mainType = build.getCirclet().getMainType();
			if ("hpP".equals(mainType))
			{
				healthStat = Math.round(healthStat * ((mainStat / 100) + 1));
			}
			else if ("attackP".equals(mainType))
			{
				attackStat = Math.round(attackStat * ((mainStat / 100) + 1));
			}
			else if ("defenseP".equals(mainType))
			{
				defenseStat = Math.round(defenseStat * ((mainStat / 100) + 1));
			}
			else if ("elemental_mastery".equals(mainType))
			{
				emStat += mainStat;
			}
			else if ("crit_rate".equals(mainType))
			{
				critRateStat += mainStat;
			}
			else if ("crit_damage".equals(mainType))
			{
				critDamageStat += mainStat;
			}
			else if ("healing_bonus".equals(mainType))
			{
				healingBonusStat += mainStat;
			}

			model.addAttribute("healthStat", healthStat);
			model.addAttribute("attackStat", attackStat);
			model.addAttribute("defenseStat", defenseStat);
			model.addAttribute("emStat", emStat);
			model.addAttribute("erStat", erStat);
			model.addAttribute("critRateStat", critRateStat);
			model.addAttribute("critDamageStat", critDamageStat);
			model.addAttribute("healingBonusStat", healingBonusStat);

			model.addAttribute("anemoDamageStat", anemoDamageStat);
			model.addAttribute("cryoDamageStat", cryoDamageStat);
			model.addAttribute("electroDamageStat", electroDamageStat);
			model.addAttribute("geoDamageStat", geoDamageStat);
			model.addAttribute("hydroDamageStat", hydroDamageStat);
			model.addAttribute("pyroDamageStat", pyroDamageStat);
			model.addAttribute("physicalDamageStat", physicalDamageStat);

			return "viewCharacter";
		}

		return "error";
	}

	@GetMapping("/viewBuild/{id}")
	public String getCharacterBuildsPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin") && !Utils.checkLogin(session, "user"))
			return "loginErrorUnauth";

		List<Build> builds = buildService.findByUsersId((int) (session.getAttribute("id")));

		Build build = new Build();

		for (int i = 0; i < builds.size(); i++)
		{
			if (builds.get(i).getCharacter().getId() == id)
			{
				build = builds.get(i);
			}
		}
		GenshinCharacter genshinCharacter = build.getCharacter();

		model.addAttribute("genshinCharacter", genshinCharacter);
		model.addAttribute("build", build);
		return "viewBuilds";
	}

}
