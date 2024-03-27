package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VorlesungsverzeichnisRepository extends JpaRepository<Vorlesung, Long> {
    List<Vorlesung> findBySemesterEquals(int semester);

    List<Vorlesung> findByBezeichnungIgnoreCaseContaining(String Bezeichnung);

    List<Vorlesung> findByBezeichnungIgnoreCaseContainingAndSemesterEquals(String bezeichnung, int semester);

}
