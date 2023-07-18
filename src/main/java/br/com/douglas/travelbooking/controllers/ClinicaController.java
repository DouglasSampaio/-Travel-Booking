package br.com.douglas.travelbooking.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping
	public ResponseEntity<List<ClinicaDTO>> findAll() {
		List<Clinica> list = service.findAll();
		List<ClinicaDTO> listDto = list.stream().map(listClinica -> new ClinicaDTO(listClinica)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/{idClinica}")
	public ResponseEntity<ClinicaDTO> findByUserId(@PathVariable int idClinica) {
	    Clinica clinica = service.findByClinicaId(idClinica);
	    if (clinica != null) {
	    	ClinicaDTO clinicaDto = new ClinicaDTO(
	    			clinica.getIdClinica(),
	    			clinica.getNomeClinica(),
	    			clinica.getEndereco().getNumero(),
	    			clinica.getEndereco().getCep(),
	    			clinica.getEndereco().getLogradouro(),
	    			clinica.getEndereco().getComplemento(),
	    			clinica.getEndereco().getBairro(),
	    			clinica.getEndereco().getUf()
	    			);
	        return ResponseEntity.ok(clinicaDto);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
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
