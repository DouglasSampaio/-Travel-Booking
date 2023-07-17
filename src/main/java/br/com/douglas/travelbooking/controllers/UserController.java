package br.com.douglas.travelbooking.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.travelbooking.dto.UserDTO;
import br.com.douglas.travelbooking.model.User;
import br.com.douglas.travelbooking.services.UserService;

@RestController
@RequestMapping(value = "/v1/usuarios")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(listUser -> new UserDTO(listUser)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
}
