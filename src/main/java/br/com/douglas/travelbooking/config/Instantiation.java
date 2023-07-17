package br.com.douglas.travelbooking.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.douglas.travelbooking.model.User;
import br.com.douglas.travelbooking.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.deleteAll();
		
		User douglas = new User(null, 1, "Douglas", "Douglas@gmail.com", "canavial");
		User matue = new User(null, 2, "Matue", "Matue@gmail.com", "canavial");
		
		userRepository.saveAll(Arrays.asList(douglas,matue));
	}

}
