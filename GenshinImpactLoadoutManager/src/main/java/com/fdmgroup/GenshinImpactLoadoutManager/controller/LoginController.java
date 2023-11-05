package com.fdmgroup.GenshinImpactLoadoutManager.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
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
public class LoginController
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

	@Autowired
	public LoginController(BuildService buildService, GenshinCharacterService genshinCharacterService,
			UsersService usersService, WeaponService weaponService, ArtifactCircletService artifactCircletService,
			ArtifactFeatherService artifactFeatherService, ArtifactFlowerService artifactFlowerService,
			ArtifactGobletService artifactGobletService, ArtifactSandsService artifactSandsService)
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
	}

	@GetMapping("/login")
	public String login(Model model, HttpSession session)
	{
		if (Utils.checkLogin(session, "admin"))
			return "redirect:/createPage";

		if (Utils.checkLogin(session, "user"))
		{
			List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
			model.addAttribute("genshinCharacters", genshinCharacters);
			return "allCharacters";
		}

		Users user = new Users();
		model.addAttribute("user", user);

		return "login";
	}

	@PostMapping("/login")
	public String processLogin(HttpSession session, @RequestParam String username, @RequestParam String password,
			Model model) throws NoSuchAlgorithmException
	{
		Users user1 = usersService.findByUsername(username);

		System.out.println(user1.getPassword());
		System.out.println(Utils.toHexString(Utils.getSHA(password)));
		System.out.println(password);

		if (user1 == null || !user1.getUsername().equals(username)
				|| !user1.getPassword().equals(Utils.encryptPass(password)))
		{
			return "loginErrorCredInvalid";
		}

		session.setAttribute("role", user1.getRole());
		session.setAttribute("id", user1.getId());

		logger.info(user1.getRole() + " : " + user1.getUsername() + " LOGGED IN");

		if (user1.getRole().equals("admin"))
		{
			return "redirect:/createPage";
		}
		else
		{
			List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
			model.addAttribute("genshinCharacters", genshinCharacters);
			return "allCharacters";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		session.invalidate();

		logger.info("LOGGED OUT");

		return "loggedOut";
	}

	@GetMapping("/createUser")
	public String createUserPage(HttpSession session, Model model, HttpServletRequest request)
	{
		String referer = request.getHeader("Referer");
		String[] test = referer.split("/");
		String prev = test[test.length - 1];

		if (prev.equals("createPage?") || prev.equals("createPage"))
		{
		}
		else if (Utils.checkLogin(session, "admin") || Utils.checkLogin(session, "user"))
		{
			logger.warn("Already Logged In");
			return "errorAlreadyLoggedIn";
		}

		model.addAttribute("user", new Users());

		return "createUser";
	}

	@PostMapping("/createUserS")
	public String processCreateUser(@ModelAttribute Users user, Model model) throws NoSuchAlgorithmException
	{
		System.out.println(user.getPassword());
		Users user1 = new Users();
		user1 = user;

		List<Users> users = usersService.getAll();

		for (int i = 0; i < users.size(); i++)
		{
			if (user.getUsername().equals(users.get(i).getUsername()))
			{
				logger.info("ERROR, CONFLICTING USERNAME: " + user1.getUsername());
				return "errorConflictUsername";
			}
		}

		user1.setRole("user");
		user1.setPassword(Utils.encryptPass(user.getPassword()));
		user1.setId(users.size());
		usersService.save(user1);

		List<GenshinCharacter> genshinCharacters = genshinCharacterService.getAll();
		Build build = buildService.getAll().get(buildService.getAll().size() - 1);

		for (int i = 0; i < genshinCharacters.size(); i++)
		{
			Build temp = new Build();

			temp.setId(build.getId() + i + 1);

			temp.setUsers(user1);
			temp.setCharacter(genshinCharacters.get(i));
			temp.setWeapon(weaponService.findById(-1));
			temp.setFeather(artifactFeatherService.findById(-1));
			temp.setFlower(artifactFlowerService.findById(-1));
			temp.setSands(artifactSandsService.findById(-1));
			temp.setGoblet(artifactGobletService.findById(-1));
			temp.setCirclet(artifactCircletService.findById(-1));

			buildService.save(temp);
		}

		model.addAttribute("genshinCharacters", genshinCharacters);
		logger.info("CREATED NEW USER: " + user1.getUsername());

		return "allCharacters";
	}

	@GetMapping("/deleteUser")
	public String getDeleteUserPage(HttpSession session, Model model)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		List<Users> users = usersService.getAll();

		model.addAttribute("user", users);

		return "deleteUsers";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUserPage(HttpSession session, Model model, @PathVariable int id)
	{
		if (!Utils.checkLogin(session, "admin"))
			return "loginErrorUnauth";

		if (id == -1)
		{
			return "deleteZero";
		}

//		List<Build> builds = buildService.findByUsersId(id);
//		for (int i = 0; i < builds.size(); i++)
//		{
//			buildService.delete(builds.get(i).getId());
//			logger.info("USER " + builds.get(i).getUsers().getUsername() + " CHAR " + builds.get(i).getCharacter().getName());
//		}

		Users user = usersService.findById(id);
		usersService.delete(user.getId());

		logger.info("USER " + user.getUsername() + " DELETED!");

		return "redirect:/createPage";
	}
}
