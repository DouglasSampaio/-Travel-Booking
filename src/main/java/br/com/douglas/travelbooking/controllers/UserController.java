package br.com.douglas.travelbooking.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		String email = objUserDto.getEmail();

		User existingUserEmail = service.findByEmail(email);
		if (existingUserEmail != null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		int id = (int) service.getUserCount();
		String nome = objUserDto.getNome();
		UserCreateDTO response = new UserCreateDTO(id, nome, email);
		User objUser = service.fromDTO(objUserDto);
		objUser.setIdUser(id);
		objUser = service.insert(objUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{idUser}")
	public ResponseEntity<UserCreateDTO> atualizarUsuario(@PathVariable int idUser,
			@RequestBody UserDTO userDtoAtualizado) {
		String email = userDtoAtualizado.getEmail();

		User usuarioExistente = service.findByUserId(idUser);
		User existingUserEmail = service.findByEmail(email);
		if (usuarioExistente == null) {
			return ResponseEntity.notFound().build();
		}
		if (existingUserEmail != null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}
		usuarioExistente.setNome(userDtoAtualizado.getNome());
		usuarioExistente.setEmail(userDtoAtualizado.getEmail());

		User usuarioAtualizado = service.update(usuarioExistente);

		UserCreateDTO response = new UserCreateDTO(usuarioAtualizado.getIdUser(), usuarioAtualizado.getNome(),
				usuarioAtualizado.getEmail());

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{idUser}")
	public ResponseEntity<Void> deleteUser(@PathVariable int idUser) {
		User existingUser = service.findByUserId(idUser);
		if (existingUser != null) {
			service.deleteUser(existingUser);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
