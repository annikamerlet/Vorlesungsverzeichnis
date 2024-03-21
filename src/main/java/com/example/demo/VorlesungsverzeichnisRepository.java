package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VorlesungsverzeichnisRepository extends CrudRepository<Vorlesung, Long> {
    @Override
    List<Vorlesung> findAll();
}
