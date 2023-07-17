package br.com.douglas.travelbooking.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id", "idUser"})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private int idUser;
	private String nome;
	private String email;
	private String senha;
	
}