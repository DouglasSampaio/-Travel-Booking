package br.com.douglas.travelbooking.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.travelbooking.model.User;

@RestController
@RequestMapping(value = "/v1/viagens")
public class UserController {

	@GetMapping
	public ResponseEntity <List<User>> findAll() {
		User douglas = new User("1", 1, "Douglas", "Douglas@gmail.com", "canavialderola");
		User matue = new User("2", 2, "Matue", "Matue@gmail.com", "canavialderola");
		List<User> list = new ArrayList<>();
		list.addAll(Arrays.asList(douglas, matue));
		return ResponseEntity.ok().body(list);
	}
}
