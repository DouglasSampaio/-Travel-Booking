package br.com.douglas.travelbooking.model;

import java.io.Serializable;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "idUser"})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@NonNull
	private int idUser;
	@NonNull
	private String nome;
	@NonNull
	private String email;
	@NonNull
	private String senha;
}