package br.com.douglas.travelbooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.travelbooking.model.Clinica;
import br.com.douglas.travelbooking.services.ClinicaService;

@RestController
@RequestMapping(value = "/v1/clinicas")
public class ClinicaController {
	
	@Autowired
	private ClinicaService service;
	
	
	@PostMapping
	public ResponseEntity<Clinica> insert(@RequestBody Clinica objClinicaDto) {
	 
	    int id = (int) service.getClinicaCount();
	    String nome = objClinicaDto.getNomeClinica();
	    Clinica response = new Clinica(id, nome);
	    Clinica objUser = service.fromDTO(objClinicaDto);
	    objUser.setIdClinica(id);
	    objUser = service.insert(objUser);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
