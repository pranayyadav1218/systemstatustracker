package com.pyadav.systemstatustracker.repositories;

import java.util.List;
import java.util.Optional;

import com.pyadav.systemstatustracker.models.ComponentModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends MongoRepository<ComponentModel, String> {
    public Optional<List<ComponentModel>> findAllByUserIdAndSystemId(String userId, String systemId);
    public Optional<ComponentModel> findByIdAndUserIdAndSystemId(String id, String userId, String systemId);
}
