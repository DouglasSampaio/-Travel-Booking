package br.com.douglas.travelbooking.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String numero;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String uf;
}