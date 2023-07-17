package br.com.douglas.travelbooking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.douglas.travelbooking.dto.UserDTO;
import br.com.douglas.travelbooking.model.User;
import br.com.douglas.travelbooking.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public  List<User> findAll(){
		return repository.findAll();
	}
	
	public User insert(User obj) {
		return repository.insert(obj);
	}
	
	public User findByUserId(int idUser) {
	    return repository.findByUserId(idUser);
	}
	
	public User findByEmail(String email) {
	    return repository.findByEmail(email);
	}
	 
	public long getUserCount() {
        return repository.count() + 1;
    }
	
	public User update(User user) {
        return repository.save(user);
    }
	
	public User fromDTO(UserDTO userDto) {
		return new User(
				userDto.getIdUser(),
				userDto.getNome(),
				userDto.getEmail(),
				userDto.getSenha());
	}
}
