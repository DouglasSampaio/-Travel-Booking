package br.com.douglas.travelbooking.dto;

import java.io.Serializable;

import br.com.douglas.travelbooking.model.Clinica;
import br.com.douglas.travelbooking.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicaCreateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idClinica;
	private String nomeClinica;
	private String numero;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String uf;

	public ClinicaCreateDTO(Clinica clinica) {
		idClinica = clinica.getIdClinica();
		nomeClinica = clinica.getNomeClinica();
		Endereco endereco = clinica.getEndereco();
		if (endereco != null) {
			numero = endereco.getNumero();
			cep = endereco.getCep();
			logradouro = endereco.getLogradouro();
			complemento = endereco.getComplemento();
			bairro = endereco.getBairro();
			uf = endereco.getUf();
		}
	}
}
