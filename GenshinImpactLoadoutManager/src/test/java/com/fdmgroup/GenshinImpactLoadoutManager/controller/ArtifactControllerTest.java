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

@ExtendWith(MockitoExtension.class)
public class ArtifactControllerTest
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

	private ArtifactController artifactController;

	String nextPage;

	@BeforeEach
	public void setup()
	{
		mockSession = new MockHttpSession();

		artifactController = new ArtifactController(mockBuildService, mockGenshinCharacterService, mockUsersService,
				mockWeaponService, mockArtifactCircletService, mockArtifactFeatherService, mockArtifactFlowerService,
				mockArtifactGobletService, mockArtifactSandsService);

		nextPage = new String();
	}

	@Test
	public void getDeleteFeatherPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<ArtifactFeather> feathers = mockArtifactFeatherService.getAll();

		nextPage = artifactController.getDeleteFeatherPage(mockSession, mockModel);

		verify(mockModel).addAttribute("artifactFeathers", feathers);
		assertEquals("deleteFeathers", nextPage);
	}

	@Test
	public void getDeleteFeatherPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");
		List<ArtifactFeather> feathers = mockArtifactFeatherService.getAll();

		nextPage = artifactController.getDeleteFeatherPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactFeathers", feathers);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteFeatherPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.getDeleteFeatherPage(mockSession, mockModel);

		List<ArtifactFeather> feathers = mockArtifactFeatherService.getAll();

		verify(mockModel, never()).addAttribute("artifactFeathers", feathers);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteFeatherPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = 1;
		ArtifactFeather feather = new ArtifactFeather();
		when(mockArtifactFeatherService.findById(id)).thenReturn(feather);
		List<Build> builds = mockBuildService.findByArtifactFeatherId(id);

		nextPage = artifactController.deleteFeatherPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteFeatherPage_delete_zero_not_valid()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;
		nextPage = artifactController.deleteFeatherPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("deleteZero", nextPage);
	}

	@Test
	public void deleteFeatherPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = artifactController.deleteFeatherPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteFeatherPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = artifactController.deleteFeatherPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteFlowerPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<ArtifactFlower> flowers = mockArtifactFlowerService.getAll();

		nextPage = artifactController.getDeleteFlowerPage(mockSession, mockModel);

		verify(mockModel).addAttribute("artifactFlowers", flowers);
		assertEquals("deleteFlowers", nextPage);
	}

	@Test
	public void getDeleteFlowerPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");
		List<ArtifactFlower> flowers = mockArtifactFlowerService.getAll();

		nextPage = artifactController.getDeleteFlowerPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactFlowers", flowers);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteFlowerPage_not_valid_for_not_loggedIn()
	{
		List<ArtifactFlower> flowers = mockArtifactFlowerService.getAll();

		nextPage = artifactController.getDeleteFlowerPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactFlowers", flowers);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteFlowerPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = 1;
		ArtifactFlower flower = new ArtifactFlower();
		when(mockArtifactFlowerService.findById(id)).thenReturn(flower);
		List<Build> builds = mockBuildService.findByArtifactFlowerId(id);

		nextPage = artifactController.deleteFlowerPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteFlowerPage_delete_zero_not_valid()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;

		nextPage = artifactController.deleteFlowerPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("deleteZero", nextPage);
	}

	@Test
	public void deleteFlowerPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = artifactController.deleteFlowerPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteFlowerPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = artifactController.deleteFlowerPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteSandsPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<ArtifactSands> sandss = mockArtifactSandsService.getAll();

		nextPage = artifactController.getDeleteSandsPage(mockSession, mockModel);

		verify(mockModel).addAttribute("artifactSandss", sandss);
		assertEquals("deleteSandss", nextPage);
	}

	@Test
	public void getDeleteSandsPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");
		List<ArtifactSands> sandss = mockArtifactSandsService.getAll();

		nextPage = artifactController.getDeleteSandsPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactSandss", sandss);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteSandsPage_not_valid_for_not_loggedIn()
	{
		List<ArtifactSands> sandss = mockArtifactSandsService.getAll();

		nextPage = artifactController.getDeleteSandsPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactSandss", sandss);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteSandsPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = 1;
		ArtifactSands sands = new ArtifactSands();
		when(mockArtifactSandsService.findById(id)).thenReturn(sands);
		List<Build> builds = mockBuildService.findByArtifactSandsId(id);

		nextPage = artifactController.deleteSandsPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteSandsPage_delete_zero_not_valid()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;

		nextPage = artifactController.deleteSandsPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("deleteZero", nextPage);
	}

	@Test
	public void deleteSandsPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = artifactController.deleteSandsPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteSandsPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = artifactController.deleteSandsPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteGobletPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<ArtifactGoblet> goblets = mockArtifactGobletService.getAll();

		nextPage = artifactController.getDeleteGobletPage(mockSession, mockModel);

		verify(mockModel).addAttribute("artifactGoblet", goblets);
		assertEquals("deleteGoblets", nextPage);
	}

	@Test
	public void getDeleteGobletPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");
		List<ArtifactGoblet> goblets = mockArtifactGobletService.getAll();

		nextPage = artifactController.getDeleteGobletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactGoblet", goblets);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteGobletPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.getDeleteGobletPage(mockSession, mockModel);
		List<ArtifactGoblet> goblets = mockArtifactGobletService.getAll();

		nextPage = artifactController.getDeleteGobletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactGoblet", goblets);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteGobletPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = 1;
		ArtifactGoblet goblet = new ArtifactGoblet();
		when(mockArtifactGobletService.findById(id)).thenReturn(goblet);
		List<Build> builds = mockBuildService.findByArtifactGobletId(id);

		nextPage = artifactController.deleteGobletPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteGobletPage_delete_zero_not_valid()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;
		nextPage = artifactController.deleteGobletPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("deleteZero", nextPage);
	}

	@Test
	public void deleteGobletPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = artifactController.deleteGobletPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteGobletPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = artifactController.deleteGobletPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteCircletPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		List<ArtifactCirclet> circlets = mockArtifactCircletService.getAll();

		nextPage = artifactController.getDeleteCircletPage(mockSession, mockModel);

		verify(mockModel).addAttribute("artifactCirclet", circlets);
		assertEquals("deleteCirclets", nextPage);
	}

	@Test
	public void getDeleteCircletPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");
		List<ArtifactCirclet> circlets = mockArtifactCircletService.getAll();

		nextPage = artifactController.getDeleteCircletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactCirclet", circlets);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getDeleteCircletPage_not_valid_for_not_loggedIn()
	{
		List<ArtifactCirclet> circlets = mockArtifactCircletService.getAll();

		nextPage = artifactController.getDeleteCircletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute("artifactCirclet", circlets);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteCircletPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");
		int id = 1;
		ArtifactCirclet circlet = new ArtifactCirclet();
		when(mockArtifactCircletService.findById(id)).thenReturn(circlet);
		List<Build> builds = mockBuildService.findByArtifactCircletId(id);

		nextPage = artifactController.deleteCircletPage(mockSession, mockModel, id);

		verify(mockBuildService, times(builds.size())).delete(id);
		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void deleteCircletPage_delete_zero_notvalid()
	{
		mockSession.setAttribute("role", "admin");
		int id = -1;

		nextPage = artifactController.deleteCircletPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("deleteZero", nextPage);
	}

	@Test
	public void deleteCircletPage_notValid_for_user()
	{
		mockSession.setAttribute("role", "user");
		int id = -1;

		nextPage = artifactController.deleteCircletPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void deleteCircletPage_notValid_for_not_loggedIn()
	{
		int id = -1;

		nextPage = artifactController.deleteCircletPage(mockSession, mockModel, id);

		verify(mockBuildService, never()).delete(id);
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createFeatherPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = artifactController.createFeatherPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("feather"), any(ArtifactFeather.class));
		assertEquals("createFeather", nextPage);
	}

	@Test
	public void createFeatherPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = artifactController.createFeatherPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("feather"), any(ArtifactFeather.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createFeatherPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.createFeatherPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("feather"), any(ArtifactFeather.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateFeather()
	{
		ArtifactFeather feather = new ArtifactFeather();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = artifactController.processCreateFeather(feather, mockModel);

		verify(mockArtifactFeatherService).save(feather);
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void createFlowerPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = artifactController.createFlowerPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("flower"), any(ArtifactFlower.class));
		assertEquals("createFlower", nextPage);
	}

	@Test
	public void createFlowerPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = artifactController.createFlowerPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("flower"), any(ArtifactFlower.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createFlowerPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.createFlowerPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("flower"), any(ArtifactFlower.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateFlower()
	{
		ArtifactFlower flower = new ArtifactFlower();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = artifactController.processCreateFlower(flower, mockModel);

		verify(mockArtifactFlowerService).save(flower);
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void createSandsPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = artifactController.createSandsPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("sands"), any(ArtifactSands.class));
		assertEquals("createSands", nextPage);
	}

	@Test
	public void createSandsPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = artifactController.createSandsPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("sands"), any(ArtifactSands.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createSandsPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.createSandsPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("sands"), any(ArtifactSands.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateSands()
	{
		ArtifactSands sands = new ArtifactSands();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = artifactController.processCreateSands(sands, mockModel);

		verify(mockArtifactSandsService).save(sands);
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void createGobletPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = artifactController.createGobletPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("goblet"), any(ArtifactGoblet.class));
		assertEquals("createGoblet", nextPage);
	}

	@Test
	public void createGobletPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = artifactController.createGobletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("goblet"), any(ArtifactGoblet.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createGobletPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.createGobletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("goblet"), any(ArtifactGoblet.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateGoblet()
	{
		ArtifactGoblet goblet = new ArtifactGoblet();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = artifactController.processCreateGoblet(goblet, mockModel);

		verify(mockArtifactGobletService).save(goblet);
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void createCircletPage_valid_for_admin()
	{
		mockSession.setAttribute("role", "admin");

		nextPage = artifactController.createCircletPage(mockSession, mockModel);

		verify(mockModel).addAttribute(eq("circlet"), any(ArtifactCirclet.class));
		assertEquals("createCirclet", nextPage);
	}

	@Test
	public void createCircletPage_not_valid_for_user()
	{
		mockSession.setAttribute("role", "user");

		nextPage = artifactController.createCircletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("circlet"), any(ArtifactCirclet.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void createCircletPage_not_valid_for_not_loggedIn()
	{
		nextPage = artifactController.createCircletPage(mockSession, mockModel);

		verify(mockModel, never()).addAttribute(eq("circlet"), any(ArtifactCirclet.class));
		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void processCreateCirclet()
	{
		ArtifactCirclet circlet = new ArtifactCirclet();
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = artifactController.processCreateCirclet(circlet, mockModel);

		verify(mockArtifactCircletService).save(circlet);
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void getEditFlowerPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("role", "admin");

		List<ArtifactFlower> flowers = mockArtifactFlowerService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getFlowerBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("flowers", flowers);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editFlower", nextPage);
	}

	@Test
	public void getEditFlowerPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("role", "user");

		List<ArtifactFlower> flowers = mockArtifactFlowerService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getFlowerBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("flowers", flowers);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editFlower", nextPage);
	}

	@Test
	public void getEditFlowerPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		List<ArtifactFlower> flowers = mockArtifactFlowerService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getFlowerBuildPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute("flowers", flowers);
		verify(mockModel, never()).addAttribute("build", build);

		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getEditFeatherPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("role", "admin");

		List<ArtifactFeather> feathers = mockArtifactFeatherService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getFeatherBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("feathers", feathers);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editFeather", nextPage);
	}

	@Test
	public void getEditFeatherPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("role", "user");

		List<ArtifactFeather> feathers = mockArtifactFeatherService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getFeatherBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("feathers", feathers);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editFeather", nextPage);
	}

	@Test
	public void getEditFeatherPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		List<ArtifactFeather> feathers = mockArtifactFeatherService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getFeatherBuildPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute("feathers", feathers);
		verify(mockModel, never()).addAttribute("build", build);

		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getEditSandsPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("role", "admin");

		List<ArtifactSands> sandss = mockArtifactSandsService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getSandsBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("sands", sandss);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editSands", nextPage);
	}

	@Test
	public void getEditSandsPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("role", "user");

		List<ArtifactSands> sandss = mockArtifactSandsService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getSandsBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("sands", sandss);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editSands", nextPage);
	}

	@Test
	public void getEditSandsPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		List<ArtifactSands> sandss = mockArtifactSandsService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getSandsBuildPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute("sands", sandss);
		verify(mockModel, never()).addAttribute("build", build);

		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getEditGobletPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("role", "admin");

		List<ArtifactGoblet> goblets = mockArtifactGobletService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getGobletBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("goblets", goblets);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editGoblet", nextPage);
	}

	@Test
	public void getEditGobletPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("role", "user");

		List<ArtifactGoblet> goblets = mockArtifactGobletService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getGobletBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("goblets", goblets);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editGoblet", nextPage);
	}

	@Test
	public void getEditGobletPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		List<ArtifactGoblet> goblets = mockArtifactGobletService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getGobletBuildPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute("goblets", goblets);
		verify(mockModel, never()).addAttribute("build", build);

		assertEquals("loginErrorUnauth", nextPage);
	}

	@Test
	public void getEditCircletPage_valid_for_admin()
	{
		int id = -1;
		mockSession.setAttribute("role", "admin");

		List<ArtifactCirclet> circlets = mockArtifactCircletService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getCircletBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("circlets", circlets);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editCirclet", nextPage);
	}

	@Test
	public void getEditCircletPage_valid_for_user()
	{
		int id = -1;
		mockSession.setAttribute("role", "user");

		List<ArtifactCirclet> circlets = mockArtifactCircletService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getCircletBuildPage(mockSession, mockModel, id);

		verify(mockModel).addAttribute("circlets", circlets);
		verify(mockModel).addAttribute("build", build);

		assertEquals("editCirclet", nextPage);
	}

	@Test
	public void getEditCircletPage_not_valid_for_not_loggedIn()
	{
		int id = -1;

		List<ArtifactCirclet> circlets = mockArtifactCircletService.getAll();
		Build build = mockBuildService.findById(id);

		nextPage = artifactController.getCircletBuildPage(mockSession, mockModel, id);

		verify(mockModel, never()).addAttribute("circlets", circlets);
		verify(mockModel, never()).addAttribute("build", build);

		assertEquals("loginErrorUnauth", nextPage);
	}
}
