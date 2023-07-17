package br.com.douglas.travelbooking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.douglas.travelbooking.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	//Optional<User> findById(int idUser);
	@Query("{ 'idUser' : ?0 }")
    User findByUserId(int idUser);
}
