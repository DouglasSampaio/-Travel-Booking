package br.com.douglas.travelbooking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglas.travelbooking.dto.ClinicaDTO;
import br.com.douglas.travelbooking.model.Clinica;
import br.com.douglas.travelbooking.model.Endereco;
import br.com.douglas.travelbooking.repository.ClinicaRepository;

@Service
public class ClinicaService {
	
	@Autowired
	private ClinicaRepository repository;
	
	public Clinica insert(Clinica obj) {
		return repository.insert(obj);
	}
	
	public int getClinicaCount() {
	    Clinica lastClinica = repository.findTopByOrderByIdClinicaDesc();
	    return (lastClinica != null) ? lastClinica.getIdClinica() + 1 : 1;
	}

	
	public Clinica clinicaDTO(ClinicaDTO clinicaDto) {
	    return new Clinica(
	            clinicaDto.getIdClinica(),
	            clinicaDto.getNomeClinica(),
	            new Endereco(
	                clinicaDto.getNumero(),
	                clinicaDto.getCep(),
	                clinicaDto.getLogradouro(),
	                clinicaDto.getComplemento(),
	                clinicaDto.getBairro(),
	                clinicaDto.getUf()
	            )
	    );
	}

}
