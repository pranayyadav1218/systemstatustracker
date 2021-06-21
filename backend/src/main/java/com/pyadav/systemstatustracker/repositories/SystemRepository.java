package com.pyadav.systemstatustracker.repositories;

import java.util.List;
import java.util.Optional;

import com.pyadav.systemstatustracker.models.SystemModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends MongoRepository<SystemModel, String>  {
    public Optional<List<SystemModel>> findAllByUserId(String userId);

    //@Query("{'id' : ?0 , 'userId' : ?1}")
    public Optional<SystemModel> findByIdAndUserId(String id, String userId);
}
