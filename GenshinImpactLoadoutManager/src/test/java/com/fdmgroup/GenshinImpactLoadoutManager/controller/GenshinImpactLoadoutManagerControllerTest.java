package com.fdmgroup.GenshinImpactLoadoutManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

import com.fdmgroup.GenshinImpactLoadoutManager.config.TestingData;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactCirclet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFeather;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactFlower;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactGoblet;
import com.fdmgroup.GenshinImpactLoadoutManager.model.ArtifactSands;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Build;
import com.fdmgroup.GenshinImpactLoadoutManager.model.GenshinCharacter;
import com.fdmgroup.GenshinImpactLoadoutManager.model.Users;
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

@ExtendWith(MockitoExtension.class)
public class GenshinImpactLoadoutManagerControllerTest
{

	@Mock
	private BuildService mockBuildService;
	@Mock
	private GenshinCharacterService mockGenshinCharacterService;
	@Mock
	private UsersService mockUsersService;
	@Mock
	private WeaponService mockWeaponService;
	@Mock
	private ArtifactCircletService mockArtifactCircletService;
	@Mock
	private ArtifactFeatherService mockArtifactFeatherService;
	@Mock
	private ArtifactFlowerService mockArtifactFlowerService;
	@Mock
	private ArtifactGobletService mockArtifactGobletService;
	@Mock
	private ArtifactSandsService mockArtifactSandsService;

	@Mock
	private TestingData td;
	
	@Mock
	private MockHttpSession mockSession;

	@Mock
	private Model mockModel;

	private GenshinImpactLoadoutManagerController mainController;

	String nextPage;

	@BeforeEach
	public void setup()
	{
		mockSession = new MockHttpSession();
		mainController = new GenshinImpactLoadoutManagerController(mockBuildService, mockGenshinCharacterService,
				mockUsersService, mockWeaponService, mockArtifactCircletService, mockArtifactFeatherService,
				mockArtifactFlowerService, mockArtifactGobletService, mockArtifactSandsService, td);

		nextPage = new String();
	}

	@Test
	public void getCharactersPage_adds_all_characters_to_model_return_allCharacters()
	{
		List<GenshinCharacter> genshinCharacters = Arrays.asList(new GenshinCharacter(), new GenshinCharacter());
		when(mockGenshinCharacterService.getAll()).thenReturn(genshinCharacters);
		nextPage = mainController.getCharactersPage(mockModel);
		verify(mockGenshinCharacterService).getAll();
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void getCreatePage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		nextPage = mainController.getCreatePage(mockSession, mockModel);
		assertEquals(nextPage, "createPage");
	}

	@Test
	public void getCreatePage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		nextPage = mainController.getCreatePage(mockSession, mockModel);
		assertEquals(nextPage, "loginErrorUnauth");
	}

	@Test
	public void getCreatePage_notValid_for_not_loggedIn()
	{
		nextPage = mainController.getCreatePage(mockSession, mockModel);
		assertEquals(nextPage, "loginErrorUnauth");
	}

	@Test
	public void getDeleteCharacterPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = mainController.getDeleteCharacterPage(mockSession, mockModel);

		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("deleteCharacters", nextPage);
	}

	@Test
	public void getDeleteCharacterPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = mainController.getDeleteCharacterPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteCharacterPage_not_valid_for_not_loggedIn()
	{
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = mainController.getDeleteCharacterPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteCharacterPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;
		GenshinCharacter character = new GenshinCharacter();
		when(mockGenshinCharacterService.findById(id)).thenReturn(character);
		List<Build> builds = mockBuildService.findByCharacterId(id);

		nextPage = mainController.deleteCharacterPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteCharacterPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = mainController.deleteCharacterPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteCharacterPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = mainController.deleteCharacterPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createCharacterPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = mainController.createCharacterPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		assertEquals("createCharacter", nextPage);
	}

	@Test
	public void createCharacterPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = mainController.createCharacterPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createCharacterPage_not_valid_for_not_loggedIn()
	{
		nextPage = mainController.createCharacterPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateCharacter_valid_for_admin()
	{
		int id = -1;
		GenshinCharacter genshinCharacter = new GenshinCharacter();
		nextPage = mainController.processCreateCharacter(genshinCharacter, mockModel);

		List<Users> users = mockUsersService.getAll();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		verify(mockUsersService, times(users.size())).findById(id);

		verify(mockWeaponService, times(users.size())).findById(id);
		verify(mockArtifactFeatherService, times(users.size())).findById(id);
		verify(mockArtifactFlowerService, times(users.size())).findById(id);
		verify(mockArtifactSandsService, times(users.size())).findById(id);
		verify(mockArtifactGobletService, times(users.size())).findById(id);
		verify(mockArtifactCircletService, times(users.size())).findById(id);
		verify(mockBuildService, times(users.size())).save(any(Build.class));

		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void getCharacterPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("id", id);
		mockSession.setAttribute("role", "admin");

		Build build = new Build(new GenshinCharacter(), new Users(), new ArtifactFlower(), new ArtifactFeather(),
				new ArtifactSands(), new ArtifactGoblet(), new ArtifactCirclet(), new Weapon());
		build.setId(id);
		build.getCharacter().setId(id);

		List<Build> builds = Arrays.asList(build);

		when(mockBuildService.findByUsersId((int) mockSession.getAttribute("id"))).thenReturn(builds);

		nextPage = mainController.getCharacterPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel).addAttribute(eq("build"), any(Build.class));
		assertEquals("viewCharacter", nextPage);
	}

	@Test
	public void getCharacterPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("id", id);
		mockSession.setAttribute("role", "user");

		Build build = new Build(new GenshinCharacter(), new Users(), new ArtifactFlower(), new ArtifactFeather(),
				new ArtifactSands(), new ArtifactGoblet(), new ArtifactCirclet(), new Weapon());
		build.setId(id);
		build.getCharacter().setId(id);

		List<Build> builds = Arrays.asList(build);

		when(mockBuildService.findByUsersId((int) mockSession.getAttribute("id"))).thenReturn(builds);

		nextPage = mainController.getCharacterPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel).addAttribute(eq("build"), any(Build.class));
		assertEquals("viewCharacter", nextPage);
	}

	@Test
	public void getCharacterPage_noCharacter_Error_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("id", id);
		mockSession.setAttribute("role", "admin");

		nextPage = mainController.getCharacterPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel, never()).addAttribute(eq("build"), any(Build.class));
		assertEquals("error", nextPage);
	}

	@Test
	public void getCharacterPage_noCharacter_Error_for_user()
	{
		int id = -1;
		mockSession.setAttribute("id", id);
		mockSession.setAttribute("role", "user");

		nextPage = mainController.getCharacterPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel, never()).addAttribute(eq("build"), any(Build.class));
		assertEquals("error", nextPage);
	}

	@Test
	public void getCharacterPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = mainController.getCharacterPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel, never()).addAttribute(eq("build"), any(Build.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getCharacterBuildsPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("id", id);
		mockSession.setAttribute("role", "admin");

		Build build = new Build(new GenshinCharacter(), new Users(), new ArtifactFlower(), new ArtifactFeather(),
				new ArtifactSands(), new ArtifactGoblet(), new ArtifactCirclet(), new Weapon());
		build.setId(id);
		build.getCharacter().setId(id);

		List<Build> builds = Arrays.asList(build);
		when(mockBuildService.findByUsersId((int) mockSession.getAttribute("id"))).thenReturn(builds);

		nextPage = mainController.getCharacterBuildsPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel).addAttribute(eq("build"), any(Build.class));
		assertEquals("viewBuilds", nextPage);
	}

	@Test
	public void getCharacterBuildsPagePage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("id", id);
		mockSession.setAttribute("role", "user");

		Build build = new Build(new GenshinCharacter(), new Users(), new ArtifactFlower(), new ArtifactFeather(),
				new ArtifactSands(), new ArtifactGoblet(), new ArtifactCirclet(), new Weapon());
		build.setId(id);
		build.getCharacter().setId(id);

		List<Build> builds = Arrays.asList(build);
		when(mockBuildService.findByUsersId((int) mockSession.getAttribute("id"))).thenReturn(builds);

		nextPage = mainController.getCharacterBuildsPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel).addAttribute(eq("build"), any(Build.class));
		assertEquals("viewBuilds", nextPage);
	}

	@Test
	public void getCharacterBuildsPagePage_not_valid_for_not_loggedIn()
	{
		int id = -1;
		mockSession.setAttribute("id", id);

		nextPage = mainController.getCharacterBuildsPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute(eq("genshinCharacter"), any(GenshinCharacter.class));
		verify(mockModel, never()).addAttribute(eq("build"), any(Build.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

}
