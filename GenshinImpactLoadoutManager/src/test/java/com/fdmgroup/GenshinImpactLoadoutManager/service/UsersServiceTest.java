package com.fdmgroup.GenshinImpactLoadoutManager.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Users;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.UsersRepository;

@SpringBootTest
public class UsersServiceTest
{
	@Autowired
	private UsersService usersService;
	
	@MockBean
	private UsersRepository mockUsersRepo;
	
	@Test
	public void save_Users()
	{
		Users users = new Users();
		usersService.save(users);
		verify(mockUsersRepo).save(users);
	}
	
	@Test
	public void delete_Users()
	{
		Users users = new Users();
		int id = users.getId();
		usersService.delete(id);
		verify(mockUsersRepo).deleteById(id);
	}
	
	@Test
	public void getAll_Userss()
	{
		usersService.getAll();
		verify(mockUsersRepo).findAll();
	}

	@Test
	public void findById_Users()
	{
		Users users = new Users();
		int id = users.getId();
		usersService.findById(id);
		verify(mockUsersRepo).findById(id);
	}
	
	@Test
	public void findByUsername_Users()
	{
		Users users = new Users();
		String username = users.getUsername();
		usersService.findByUsername(username);
		verify(mockUsersRepo).findByUsername(username);
	}

}
