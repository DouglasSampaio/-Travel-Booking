package br.com.douglas.travelbooking.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.travelbooking.dto.UserCreateDTO;
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
	
	@GetMapping("/{idUser}")
	public ResponseEntity<UserCreateDTO> findByUserId(@PathVariable int idUser) {
	    User user = service.findByUserId(idUser);
	    if (user != null) {
	    	UserCreateDTO userDto = new UserCreateDTO(user.getIdUser(), user.getNome(), user.getEmail());
	        return ResponseEntity.ok(userDto);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping
	public ResponseEntity<UserCreateDTO> insert(@RequestBody UserDTO objUserDto) {
		
//		try {
//			Optional<Cartao> existingCartao = service.findByNumero(numeroCartao);
//			if (existingCartao.isPresent()) {
//				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
//			}
//			Cartao obj = service.newCartaoDto(cartaoDTO);
//			obj = service.insert(obj);
//
//			return ResponseEntity.status(HttpStatus.CREATED).body(response);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
//		}
		int id = (int) service.getUserCount();
		String nome = objUserDto.getNome();
		String email = objUserDto.getEmail();
		UserCreateDTO response = new UserCreateDTO(id, nome, email);
		User objUser = service.fromDTO(objUserDto);
		objUser.setIdUser(id);
		objUser = service.insert(objUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
