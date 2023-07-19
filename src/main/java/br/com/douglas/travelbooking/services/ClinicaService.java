package br.com.douglas.travelbooking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.douglas.travelbooking.dto.ClinicaCreateDTO;
import br.com.douglas.travelbooking.dto.ClinicaDTO;
import br.com.douglas.travelbooking.dto.EnderecoDTO;
import br.com.douglas.travelbooking.model.Clinica;
import br.com.douglas.travelbooking.model.Endereco;
import br.com.douglas.travelbooking.repository.ClinicaRepository;

@Service
public class ClinicaService {

	@Autowired
	private ClinicaRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${api.cep.url}")
	private String cepApiUrl;

	public List<ClinicaDTO> findAll() {
		List<Clinica> list = repository.findAll();
		return list.stream().map(listClinica -> {
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
	}

	public ClinicaDTO insert(ClinicaCreateDTO clinicaDto) {
		Endereco apiCep = consultarCep(clinicaDto.getCep());

		if (apiCep == null) {
			throw new RuntimeException("Não foi possível obter informações do CEP: " + clinicaDto.getCep());
		}

		int id = getClinicaCount();
		ClinicaDTO response = new ClinicaDTO(id, clinicaDto.getNomeClinica(),
				new EnderecoDTO(clinicaDto.getNumero(), clinicaDto.getCep(), apiCep.getLogradouro(),
						apiCep.getComplemento(), apiCep.getBairro(), apiCep.getUf()));

		Clinica objClinica = clinicaCreateDTO(clinicaDto);
		objClinica.setIdClinica(id);
		objClinica = repository.insert(objClinica);

		return response;
	}

	public Clinica findByClinicaId(int idClinica) {
		return repository.findByClinicaId(idClinica);
	}

	public ClinicaDTO update(int id, ClinicaCreateDTO clinicaDto) {
		Clinica existingClinica = repository.findByClinicaId(id);

		if (existingClinica == null) {
			throw new RuntimeException("Clínica com ID " + id + " não encontrada.");
		}

		Endereco apiCep = consultarCep(clinicaDto.getCep());

		if (apiCep == null) {
			throw new RuntimeException("Não foi possível obter informações do CEP: " + clinicaDto.getCep());
		}

		existingClinica.setNomeClinica(clinicaDto.getNomeClinica());
		existingClinica.getEndereco().setNumero(clinicaDto.getNumero());
		existingClinica.getEndereco().setCep(clinicaDto.getCep());
		existingClinica.getEndereco().setLogradouro(apiCep.getLogradouro());
		existingClinica.getEndereco().setComplemento(apiCep.getComplemento());
		existingClinica.getEndereco().setBairro(apiCep.getBairro());
		existingClinica.getEndereco().setUf(apiCep.getUf());

		existingClinica = repository.save(existingClinica);

		return new ClinicaDTO(existingClinica);
	}

	public void deleteClinica(int idClinica) {
		repository.deleteById(idClinica);
	}

	public int getClinicaCount() {
		Clinica lastClinica = repository.findTopByOrderByIdClinicaDesc();
		return (lastClinica != null) ? lastClinica.getIdClinica() + 1 : 1;
	}

	public Clinica clinicaDTO(ClinicaDTO clinicaDto) {
		Endereco apiCep = consultarCep(clinicaDto.getEndereco().getCep());
		return new Clinica(clinicaDto.getIdClinica(), clinicaDto.getNomeClinica(),
				new Endereco(clinicaDto.getEndereco().getNumero(), clinicaDto.getEndereco().getCep(),
						apiCep.getLogradouro(), apiCep.getComplemento(), apiCep.getBairro(), apiCep.getUf()));
	}

	public Clinica clinicaCreateDTO(ClinicaCreateDTO clinicaDto) {
		Endereco apiCep = consultarCep(clinicaDto.getCep());
		return new Clinica(clinicaDto.getIdClinica(), clinicaDto.getNomeClinica(),
				new Endereco(clinicaDto.getNumero(), clinicaDto.getCep(), apiCep.getLogradouro(),
						apiCep.getComplemento(), apiCep.getBairro(), apiCep.getUf()));
	}

	public Endereco consultarCep(String cep) {

		String url = cepApiUrl + cep + "/json";

		ResponseEntity<Endereco> response = restTemplate.getForEntity(url, Endereco.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new RuntimeException("Não foi possível consultar o CEP: " + cep);
		}
	}

}
