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
public class ClinicaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idClinica;
	private String nomeClinica;
	private EnderecoDTO endereco;

	public ClinicaDTO(Clinica clinica) {
		idClinica = clinica.getIdClinica();
		nomeClinica = clinica.getNomeClinica();
		Endereco endereco = clinica.getEndereco();
		if (endereco != null) {
			EnderecoDTO enderecoDto = new EnderecoDTO();
			enderecoDto.setNumero(endereco.getNumero());
			enderecoDto.setCep(endereco.getCep());
			enderecoDto.setLogradouro(endereco.getLogradouro());
			enderecoDto.setComplemento(endereco.getComplemento());
			enderecoDto.setBairro(endereco.getBairro());
			enderecoDto.setUf(endereco.getUf());
			this.endereco = enderecoDto;
		}
	}

}
