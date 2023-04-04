package com.servervaultapp.servervaultapp.repositories;

import com.servervaultapp.servervaultapp.entities.Server;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServerRepository extends MongoRepository<Server, String> {

    public List<Server> findAllByName(String name);
}
