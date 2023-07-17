package br.com.douglas.travelbooking.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Endereco {
	private String numero;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String uf;
}
