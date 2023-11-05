package com.fdmgroup.GenshinImpactLoadoutManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

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

@ExtendWith(MockitoExtension.class)
public class WeaponControllerTest
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
	private MockHttpSession mockSession;

	@Mock
	private Model mockModel;

	private WeaponController weaponController;

	String nextPage;

	@BeforeEach
	public void setup()
	{
		mockSession = new MockHttpSession();

		weaponController = new WeaponController(mockBuildService, mockGenshinCharacterService, mockUsersService,
				mockWeaponService, mockArtifactCircletService, mockArtifactFeatherService, mockArtifactFlowerService,
				mockArtifactGobletService, mockArtifactSandsService);

		nextPage = new String();
	}

	@Test
	public void getDeleteWeaponPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<Weapon> weapons = mockWeaponService.getAll();

		nextPage = weaponController.getDeleteWeaponPage(mockSession, mockModel);

		verify(mockModel).addAttribute("weapons", weapons);
		assertEquals("deleteWeapons", nextPage);
	}

	@Test
	public void getDeleteWeaponPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = weaponController.getDeleteWeaponPage(mockSession, mockModel);
		List<Weapon> weapons = mockWeaponService.getAll();

		verify(mockModel, never()).addAttribute("weapons", weapons);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteWeaponPage_not_valid_for_not_loggedIn()
	{
		nextPage = weaponController.getDeleteWeaponPage(mockSession, mockModel);
		List<Weapon> weapons = mockWeaponService.getAll();

		verify(mockModel, never()).addAttribute("weapons", weapons);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteWeaponPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = 1;
		Weapon weapon = new Weapon();
		when(mockWeaponService.findById(id)).thenReturn(weapon);
		List<Build> builds = mockBuildService.findByWeaponId(id);

		nextPage = weaponController.deleteWeaponPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteWeaponPage_delete_zero_not_valid()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;

		nextPage = weaponController.deleteWeaponPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("deleteZero", nextPage);
	}

	@Test
	public void deleteWeaponPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = weaponController.deleteWeaponPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteWeaponPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = weaponController.deleteWeaponPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createWeaponPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = weaponController.createWeaponPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("weapon"), any(Weapon.class));
		assertEquals("createWeapon", nextPage);
	}

	@Test
	public void createWeaponPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = weaponController.createWeaponPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("weapon"), any(Weapon.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createWeaponPage_not_valid_for_not_loggedIn()
	{
		nextPage = weaponController.createWeaponPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("weapon"), any(Weapon.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateWeapon()
	{
		Weapon weapon = new Weapon();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = weaponController.processCreateWeapon(weapon, mockModel);

		verify(mockWeaponService).save(weapon);
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void getEditWeaponsPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("role", "admin");

		GenshinCharacter character = new GenshinCharacter();
		Build build = new Build();
		build.setCharacter(character);

		List<Weapon> weapons = mockWeaponService.findByWeaponType(any(String.class));

		when(mockBuildService.findById(id)).thenReturn(build);
		when(mockWeaponService.findByWeaponType(build.getCharacter().getWeaponType())).thenReturn(weapons);

		nextPage = weaponController.getEditWeaponsPage(mockSession, mockModel, id);

		verify(mockBuildService).findById(id);
		verify(mockWeaponService).findById(id);
		verify(mockModel).addAttribute("weapons", weapons);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editWeapon", nextPage);
	}

	@Test
	public void getEditWeaponsPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("role", "user");

		GenshinCharacter character = new GenshinCharacter();
		Build build = new Build();
		build.setCharacter(character);

		List<Weapon> weapons = mockWeaponService.findByWeaponType(any(String.class));

		when(mockBuildService.findById(id)).thenReturn(build);
		when(mockWeaponService.findByWeaponType(build.getCharacter().getWeaponType())).thenReturn(weapons);

		nextPage = weaponController.getEditWeaponsPage(mockSession, mockModel, id);

		verify(mockBuildService).findById(id);
		verify(mockWeaponService).findById(id);
		verify(mockModel).addAttribute("weapons", weapons);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editWeapon", nextPage);
	}

	@Test
	public void getEditWeaponsPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		Build build = new Build();
		List<Weapon> weapons = mockWeaponService.findByWeaponType(any(String.class));

		nextPage = weaponController.getEditWeaponsPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).findById(id);
		verify(mockWeaponService, never()).findById(id);
		verify(mockModel, never()).addAttribute("weapons", weapons);
		verify(mockModel, never()).addAttribute("build", build);

		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void editBuildWeapon_valid_for_admin()
	{
		int id = -1;
		int id2 = 1;

		mockSession.setAttribute("role", "admin");

		Weapon weapon = new Weapon();
		weapon.setId(id);
		weapon.setWeaponType("sword");

		GenshinCharacter genshinCharacter = new GenshinCharacter();
		genshinCharacter.setWeaponType("sword");

		Build build = new Build();
		build.setCharacter(genshinCharacter);
		build.setId(id2);

		int redirectBuild = build.getCharacter().getId();

		when(mockWeaponService.findById(id)).thenReturn(weapon);
		when(mockBuildService.findById(id2)).thenReturn(build);

		nextPage = weaponController.editBuildWeapon(mockSession, id, id2);

		verify(mockWeaponService).findById(id);
		verify(mockBuildService).findById(id2);
		verify(mockBuildService).save(build);

		assertEquals("redirect:/viewBuild/" + redirectBuild, nextPage);
	}

	@Test
	public void editBuildWeapon_valid_for_user()
	{
		int id = -1;
		int id2 = 1;

		mockSession.setAttribute("role", "user");

		Weapon weapon = new Weapon();
		weapon.setId(id);
		weapon.setWeaponType("sword");

		GenshinCharacter genshinCharacter = new GenshinCharacter();
		genshinCharacter.setWeaponType("sword");

		Build build = new Build();
		build.setCharacter(genshinCharacter);
		build.setId(id2);

		int redirectBuild = build.getCharacter().getId();

		when(mockWeaponService.findById(id)).thenReturn(weapon);
		when(mockBuildService.findById(id2)).thenReturn(build);

		nextPage = weaponController.editBuildWeapon(mockSession, id, id2);

		verify(mockWeaponService).findById(id);
		verify(mockBuildService).findById(id2);
		verify(mockBuildService).save(build);

		assertEquals("redirect:/viewBuild/" + redirectBuild, nextPage);
	}

	@Test
	public void editBuildWeapon_not_valid_for_not_loggedIn()
	{
		int id = -1;
		int id2 = 1;

		Weapon weapon = new Weapon();
		weapon.setId(id);
		weapon.setWeaponType("sword");

		GenshinCharacter genshinCharacter = new GenshinCharacter();
		genshinCharacter.setWeaponType("sword");

		Build build = new Build();
		build.setId(id2);

		nextPage = weaponController.editBuildWeapon(mockSession, id, id2);

		verify(mockWeaponService, never()).findById(id);
		verify(mockBuildService, never()).findById(id2);
		verify(mockBuildService, never()).save(build);

		assertEquals("loginErrorUnauth", nextPage);
	}
}
