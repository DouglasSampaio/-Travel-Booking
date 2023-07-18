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

import br.com.douglas.travelbooking.dto.ClinicaCreateDTO;
import br.com.douglas.travelbooking.dto.ClinicaDTO;
import br.com.douglas.travelbooking.dto.EnderecoDTO;
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
		List<ClinicaDTO> listDto = list.stream().map(listClinica -> {
			ClinicaDTO clinicaDto = new ClinicaDTO();
			clinicaDto.setIdClinica(listClinica.getIdClinica());
			clinicaDto.setNomeClinica(listClinica.getNomeClinica());

			EnderecoDTO enderecoDto = new EnderecoDTO();
			enderecoDto.setNumero(listClinica.getEndereco().getNumero());
			enderecoDto.setCep(listClinica.getEndereco().getCep());
			enderecoDto.setLogradouro(listClinica.getEndereco().getLogradouro());
			enderecoDto.setComplemento(listClinica.getEndereco().getComplemento());
			enderecoDto.setBairro(listClinica.getEndereco().getBairro());
			enderecoDto.setUf(listClinica.getEndereco().getUf());

			clinicaDto.setEndereco(enderecoDto);

			return clinicaDto;
		}).collect(Collectors.toList());

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

		Endereco apiCep = service.consultarCep(objClinicaDto.getCep());
		if (apiCep == null) {
			throw new RuntimeException("Não foi possível obter informações do CEP: " + objClinicaDto.getCep());
		}
		int id = service.getClinicaCount();
		ClinicaDTO response = new ClinicaDTO(id, objClinicaDto.getNomeClinica(),
				new EnderecoDTO(objClinicaDto.getNumero(), objClinicaDto.getCep(), apiCep.getLogradouro(),
						apiCep.getComplemento(), apiCep.getBairro(), apiCep.getUf()));

		Clinica objClinica = service.clinicaCreateDTO(objClinicaDto);
		objClinica.setIdClinica(id);
		objClinica = service.insert(objClinica);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
