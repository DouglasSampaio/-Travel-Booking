package br.com.douglas.travelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinicaDTO {
	private int idClinica;
    private String nomeClinica;
    private String numero;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String uf;
}
