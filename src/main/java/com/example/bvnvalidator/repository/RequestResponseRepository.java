package com.example.bvnvalidator.repository;

import com.example.bvnvalidator.model.RequestResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestResponseRepository extends MongoRepository<RequestResponse, String> {
}
