package com.fdmgroup.GenshinImpactLoadoutManager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.GenshinImpactLoadoutManager.model.Users;
import com.fdmgroup.GenshinImpactLoadoutManager.repository.UsersRepository;

@Service
public class UsersService
{

	private UsersRepository UsersRepo;

	@Autowired
	public UsersService(UsersRepository UsersRepo)
	{
		super();
		this.UsersRepo = UsersRepo;
	}

	@Transactional
	public void save(Users Users)
	{
		UsersRepo.save(Users);
	}

	@Transactional
	public void delete(int id)
	{
		UsersRepo.deleteById(id);
	}

	public List<Users> getAll()
	{
		return UsersRepo.findAll();
	}

	public Users findById(int id)
	{
		return UsersRepo.findById(id).orElse(null);
	}

	public Users findByUsername(String username)
	{
		return UsersRepo.findByUsername(username);
	}
}
