package br.com.douglas.travelbooking.dto;

import java.io.Serializable;

import br.com.douglas.travelbooking.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idUser;
	private String nome;
	private String email;
	private String senha;

	public UserDTO(User obj) {
		idUser = obj.getIdUser();
		nome = obj.getNome();
		email = obj.getEmail();
		senha = obj.getSenha();
	}
}
