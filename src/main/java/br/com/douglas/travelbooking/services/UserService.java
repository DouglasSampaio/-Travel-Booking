package br.com.douglas.travelbooking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglas.travelbooking.model.User;
import br.com.douglas.travelbooking.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public  List<User> findAll(){
		return repository.findAll();
	}
}
