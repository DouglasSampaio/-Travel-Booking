package br.com.douglas.travelbooking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.travelbooking.dto.ClinicaCreateDTO;
import br.com.douglas.travelbooking.dto.ClinicaDTO;
import br.com.douglas.travelbooking.model.Clinica;
import br.com.douglas.travelbooking.services.ClinicaService;

@RestController
@RequestMapping(value = "/v1/clinicas")
public class ClinicaController {

	@Autowired
	private ClinicaService service;

	@GetMapping
	public ResponseEntity<List<ClinicaDTO>> findAll() {
		List<ClinicaDTO> listDto = service.findAll();
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping("/{idClinica}")
	public ResponseEntity<ClinicaDTO> findByUserId(@PathVariable int idClinica) {
		Clinica clinica = service.findByClinicaId(idClinica);
		if (clinica != null) {
			ClinicaDTO clinicaDto = new ClinicaDTO(clinica);
			return ResponseEntity.ok(clinicaDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<ClinicaDTO> insert(@RequestBody ClinicaCreateDTO objClinicaDto) {
		ClinicaDTO response = service.insert(objClinicaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClinicaDTO> update(@PathVariable int id, @RequestBody ClinicaCreateDTO objClinicaDto) {
		ClinicaDTO updatedClinica = service.update(id, objClinicaDto);
		return ResponseEntity.ok(updatedClinica);
	}

	@DeleteMapping("/{idClinica}")
	public ResponseEntity<Void> deleteClinica(@PathVariable int idClinica) {
		Clinica clinica = service.findByClinicaId(idClinica);
		if (clinica != null) {
			service.deleteClinica(idClinica);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
