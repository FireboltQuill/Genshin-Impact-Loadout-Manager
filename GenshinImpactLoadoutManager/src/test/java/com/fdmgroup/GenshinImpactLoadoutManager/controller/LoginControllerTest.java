package com.fdmgroup.GenshinImpactLoadoutManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
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

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest
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

	private LoginController loginController;

	String nextPage;

	@BeforeEach
	public void setup()
	{
		mockSession = new MockHttpSession();

		loginController = new LoginController(mockBuildService, mockGenshinCharacterService, mockUsersService,
				mockWeaponService, mockArtifactCircletService, mockArtifactFeatherService, mockArtifactFlowerService,
				mockArtifactGobletService, mockArtifactSandsService);

		
		nextPage = new String();
	}

	@Test
	public void logout()
	{
		nextPage = loginController.logout(mockSession);

		assertEquals("loggedOut", nextPage);
	}

	@Test
	public void login()
	{
		nextPage = loginController.login(mockModel, mockSession);

		verify(mockModel).addAttribute(eq("user"), any(Users.class));

		assertEquals("login", nextPage);
	}

	@Test
	public void processLogin_valid_for_not_loggedIn() throws NoSuchAlgorithmException
	{
		int id = -1;
		String username = "username";
		String password = "password";
		String password2 = password;
		password = Utils.toHexString(Utils.getSHA(password));
		String role = "admin";
		Users user = new Users(username, password, role);
		user.setId(id);

		when(mockUsersService.findByUsername(username)).thenReturn(user);

		nextPage = loginController.processLogin(mockSession, username, password2, mockModel);

		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();
		verify(mockModel, never()).addAttribute("genshinCharacters", genshinCharacters);

		assertEquals("redirect:/createPage", nextPage);
	}

	@Test
	public void processLogin_pass_for_admin() throws NoSuchAlgorithmException
	{
		int id = -1;
		String username = "username";
		String password = "password";
		String password2 = password;
		password = Utils.toHexString(Utils.getSHA(password));
		String role = "user";

		Users user = new Users(username, password, role);
		user.setId(id);

		when(mockUsersService.findByUsername(username)).thenReturn(user);

		nextPage = loginController.processLogin(mockSession, username, password2, mockModel);

		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();
		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void processLogin_fail_for_admin() throws NoSuchAlgorithmException
	{
		int id = -1;
		String username = "username";
		String password = "password";
		String username2 = "usernameWrong";
		String password2 = "passwordWrong";
		String role = "admin";

		Users user = new Users(username, password, role);
		user.setId(id);

		when(mockUsersService.findByUsername(username2)).thenReturn(user);

		nextPage = loginController.processLogin(mockSession, username2, password2, mockModel);

		assertEquals("loginErrorCredInvalid", nextPage);
	}

	@Test
	public void processLogin_fail_for_user() throws NoSuchAlgorithmException
	{
		int id = -1;
		String username = "username";
		String password = "password";
		String username2 = "usernameWrong";
		String password2 = "passwordWrong";
		String role = "user";

		Users user = new Users(username, password, role);
		user.setId(id);

		when(mockUsersService.findByUsername(username2)).thenReturn(user);

		nextPage = loginController.processLogin(mockSession, username2, password2, mockModel);

		assertEquals("loginErrorCredInvalid", nextPage);
	}

	@Test
	public void checkLogin_successful_for_admin()
	{
		String type = "admin";
		mockSession.setAttribute("role", "admin");

		assertEquals(Utils.checkLogin(mockSession, type), true);
	}

	@Test
	public void checkLogin_successful_for_user()
	{
		String type = "user";
		mockSession.setAttribute("role", "user");

		assertEquals(Utils.checkLogin(mockSession, type), true);
	}

	@Test
	public void checkLogin_unsuccessful_for_admin()
	{
		String type = "admin";
		mockSession.setAttribute("role", "user");

		assertEquals(Utils.checkLogin(mockSession, type), false);
	}

	@Test
	public void checkLogin_unsuccessful_for_user()
	{
		String type = "user";
		mockSession.setAttribute("role", "admin");

		assertEquals(Utils.checkLogin(mockSession, type), false);
	}

	@Test
	public void checkLogin_unsuccessful_for_not_loggedIn_admin()
	{
		String type = "admin";

		assertEquals(Utils.checkLogin(mockSession, type), false);
	}

	@Test
	public void checkLogin_unsuccessful_for_not_loggedIn_user()
	{
		String type = "user";

		assertEquals(Utils.checkLogin(mockSession, type), false);
	}

//	@Test
//	public void createUserPage_successful_for_notloggedIn()
//	{
//		nextPage = loginController.createUserPage(mockSession, mockModel);
//
//		verify(mockModel).addAttribute(eq("user"), any(Users.class));
//		assertEquals("createUser", nextPage);
//	}
//
//	@Test
//	public void createUserPage_unsuccessful_for_loggedIn_admin()
//	{
//		mockSession.setAttribute("role", "admin");
//
//		nextPage = loginController.createUserPage(mockSession, mockModel);
//
//		verify(mockModel, never()).addAttribute(eq("user"), any(Users.class));
//		assertEquals("errorAlreadyLoggedIn", nextPage);
//	}
//
//	@Test
//	public void createUserPage_unsuccessful_for_loggedIn_user()
//	{
//		mockSession.setAttribute("role", "user");
//
//		nextPage = loginController.createUserPage(mockSession, mockModel);
//
//		verify(mockModel, never()).addAttribute(eq("user"), any(Users.class));
//		assertEquals("errorAlreadyLoggedIn", nextPage);
//	}

	@Test
	public void processCreateUser_successful() throws NoSuchAlgorithmException
	{
		Users user = new Users("username", Utils.toHexString(Utils.getSHA("password")), "user");
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		nextPage = loginController.processCreateUser(user, mockModel);

		assertEquals(user.getRole(), "user");
		verify(mockUsersService).save(user);

		verify(mockWeaponService, times(genshinCharacters.size())).findById(-1);

		verify(mockArtifactFeatherService, times(genshinCharacters.size())).findById(-1);
		verify(mockArtifactFlowerService, times(genshinCharacters.size())).findById(-1);
		verify(mockArtifactSandsService, times(genshinCharacters.size())).findById(-1);
		verify(mockArtifactGobletService, times(genshinCharacters.size())).findById(-1);
		verify(mockArtifactCircletService, times(genshinCharacters.size())).findById(-1);
		verify(mockBuildService, times(genshinCharacters.size())).save(any(Build.class));

		verify(mockModel).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("allCharacters", nextPage);
	}

	@Test
	public void processCreateUser_unsuccessful_conflicting_username() throws NoSuchAlgorithmException
	{
		Users user = new Users("username", Utils.toHexString(Utils.getSHA("password")), "user");
		List<GenshinCharacter> genshinCharacters = mockGenshinCharacterService.getAll();

		List<Users> users = mockUsersService.getAll();
		users.add(user);

		when(mockUsersService.getAll()).thenReturn(users);

		nextPage = loginController.processCreateUser(user, mockModel);

		verify(mockUsersService, never()).save(user);

		verify(mockWeaponService, never()).findById(-1);

		verify(mockArtifactFeatherService, never()).findById(-1);
		verify(mockArtifactFlowerService, never()).findById(-1);
		verify(mockArtifactSandsService, never()).findById(-1);
		verify(mockArtifactGobletService, never()).findById(-1);
		verify(mockArtifactCircletService, never()).findById(-1);
		verify(mockBuildService, never()).save(any(Build.class));

		verify(mockModel, never()).addAttribute("genshinCharacters", genshinCharacters);
		assertEquals("errorConflictUsername", nextPage);
	}

}
