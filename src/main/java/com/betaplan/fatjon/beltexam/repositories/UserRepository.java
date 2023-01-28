package com.betaplan.fatjon.beltexam.repositories;

import com.betaplan.fatjon.beltexam.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findAll();

    Optional<User> findByEmail(String email);


}
