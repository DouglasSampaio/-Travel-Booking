package br.com.douglas.travelbooking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglas.travelbooking.model.Clinica;
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
        if (lastClinica != null) {
            return lastClinica.getIdClinica() + 1;
        } else {
            return 1;
        }
    }

	
	public Clinica fromDTO(Clinica clinicaDto) {
		return new Clinica(
				clinicaDto.getIdClinica(),
				clinicaDto.getNomeClinica()
				);
	}
}
