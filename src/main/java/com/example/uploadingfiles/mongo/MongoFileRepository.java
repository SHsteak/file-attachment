package com.example.uploadingfiles.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFileRepository extends MongoRepository<MongoFile, String> {
}
