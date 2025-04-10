package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test.model.Pet;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByUserId(Integer userId);
    List<Pet> findBySpecies(String species);
    List<Pet> findByAgeGreaterThan(Float age);
    List<Pet> findByNameContaining(String name);
}