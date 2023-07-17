package br.com.douglas.travelbooking.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int idUser;
	private String nome;
	private String email;
}
