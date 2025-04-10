package com.example.test.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.test.model.Pet;
import java.util.List;
import com.example.test.repository.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetRepository petRepo;

    //@Autowired
    //private ActivityRepository activityRepo;

    @GetMapping("")
    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer id) {
        return petRepo.findById(id)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petRepo.save(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Integer id, @RequestBody Pet pet) {
        return petRepo.findById(id).map(existing -> {
            pet.setId(id);
            return ResponseEntity.ok(petRepo.save(pet));
        }).orElse(ResponseEntity.notFound().build());
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletePet(@PathVariable Integer id) {
    //     return petRepo.findById(id).map(p -> {
    //         petRepo.delete(p);
    //         return ResponseEntity.ok().build();
    //     }).orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping("/user/{userId}")
    public List<Pet> getPetsByUser(@PathVariable Integer userId) {
        return petRepo.findByUserId(userId);
    }

    @GetMapping("/species/{species}")
    public List<Pet> getPetsBySpecies(@PathVariable String species) {
        return petRepo.findBySpecies(species);
    }

    @GetMapping("/search")
    public List<Pet> searchPets(@RequestParam String name) {
        return petRepo.findByNameContaining(name);
    }

    @GetMapping("/dogs")
    public List<Pet> getDogs() {
        return petRepo.findBySpecies("dog");
    }

    @GetMapping("/old-pets")
    public List<Pet> getOldPets() {
        return petRepo.findByAgeGreaterThan(2.0f);
    }

    // @GetMapping("/{petId}/activities")
    // public List<Activity> getActivitiesByPet(@PathVariable Integer petId) {
    //     return activityRepo.findByPetId(petId);
    // }

    @GetMapping("/search-fluff")
    public List<Pet> searchFluffPets() {
        return petRepo.findByNameContaining("fluff");
    }
}
