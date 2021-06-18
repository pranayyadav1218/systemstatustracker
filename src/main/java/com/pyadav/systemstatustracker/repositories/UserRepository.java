package com.pyadav.systemstatustracker.repositories;

import java.util.Optional;

import com.pyadav.systemstatustracker.models.UserModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    public Optional<UserModel> findByUsername(String username);
}

