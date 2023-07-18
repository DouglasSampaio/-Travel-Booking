package br.com.douglas.travelbooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.travelbooking.dto.ClinicaDTO;
import br.com.douglas.travelbooking.model.Clinica;
import br.com.douglas.travelbooking.model.Endereco;
import br.com.douglas.travelbooking.services.ClinicaService;

@RestController
@RequestMapping(value = "/v1/clinicas")
public class ClinicaController {
	
	@Autowired
	private ClinicaService service;
	
	
	@PostMapping
	public ResponseEntity<ClinicaDTO> insert(@RequestBody ClinicaDTO objClinicaDto) {
		
		Endereco apiCep = service.consultarCep(objClinicaDto.getCep());
	    int id = service.getClinicaCount();
	    ClinicaDTO response = new ClinicaDTO(
	    		id,
	    		objClinicaDto.getNomeClinica(),
	    		objClinicaDto.getNumero(),
	    		objClinicaDto.getCep(),
	    		apiCep.getLogradouro(),
	    		apiCep.getComplemento(),
	    		apiCep.getBairro(),
	    		apiCep.getUf()
	    		);
	    Clinica objClinica = service.clinicaDTO(objClinicaDto);
	    objClinica.setIdClinica(id);
	    objClinica = service.insert(objClinica);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
