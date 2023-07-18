package br.com.douglas.travelbooking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.douglas.travelbooking.model.Clinica;

@Repository
public interface ClinicaRepository extends MongoRepository<Clinica, Integer> {
	@Query("{ 'idClinica' : ?0 }")
	Clinica findByClinicaId(int idClinica);
	 Clinica findTopByOrderByIdClinicaDesc();
}

