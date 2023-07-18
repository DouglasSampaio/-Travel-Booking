package br.com.douglas.travelbooking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.douglas.travelbooking.dto.ClinicaCreateDTO;
import br.com.douglas.travelbooking.dto.ClinicaDTO;
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
	
	public  List<Clinica> findAll(){
		return repository.findAll();
	}
	
	public Clinica insert(Clinica obj) {
		return repository.insert(obj);
	}
	
	public Clinica findByClinicaId(int idClinica) {
	    return repository.findByClinicaId(idClinica);
	}
	
	public int getClinicaCount() {		
	    Clinica lastClinica = repository.findTopByOrderByIdClinicaDesc();
	    return (lastClinica != null) ? lastClinica.getIdClinica() + 1 : 1;
	}

	
	public Clinica clinicaDTO(ClinicaDTO clinicaDto) {
		Endereco apiCep = consultarCep(clinicaDto.getEndereco().getCep());
	    return new Clinica(
	            clinicaDto.getIdClinica(),
	            clinicaDto.getNomeClinica(),
	            new Endereco(
	                clinicaDto.getEndereco().getNumero(),
	                clinicaDto.getEndereco().getCep(),
	                apiCep.getLogradouro(),
	                apiCep.getComplemento(),
	                apiCep.getBairro(),
	                apiCep.getUf()
	            )
	    );
	}
	
	public Clinica clinicaCreateDTO(ClinicaCreateDTO clinicaDto) {
		Endereco apiCep = consultarCep(clinicaDto.getCep());
	    return new Clinica(
	            clinicaDto.getIdClinica(),
	            clinicaDto.getNomeClinica(),
	            new Endereco(
	                clinicaDto.getNumero(),
	                clinicaDto.getCep(),
	                apiCep.getLogradouro(),
	                apiCep.getComplemento(),
	                apiCep.getBairro(),
	                apiCep.getUf()
	            )
	    );
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
