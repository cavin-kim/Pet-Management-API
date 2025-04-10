package com.example.test;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import com.example.test.model.Pet;
import com.example.test.model.Activity;
import java.util.List;
//import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    
    @Autowired
    private EntityManager em;

    @GetMapping("")
    public List<Pet> getAllPets() {
        return em.createQuery("SELECT p FROM Pet p", Pet.class).getResultList();
    }

    // Find pet by ID (Active Record: Pet.find(1))
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer id) {
        Pet pet = em.find(Pet.class, id);
        return pet != null ? ResponseEntity.ok(pet) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        em.persist(pet);
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Pet> updatePet(@PathVariable Integer id, @RequestBody Pet pet) {
        Pet existing = em.find(Pet.class, id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        pet.setId(id);
        return ResponseEntity.ok(em.merge(pet));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePet(@PathVariable Integer id) {
        Pet pet = em.find(Pet.class, id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        em.remove(pet);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<Pet> getPetsByUser(@PathVariable Integer userId) {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.user_id = :userId", Pet.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    @GetMapping("/species/{species}")
    public List<Pet> getPetsBySpecies(@PathVariable String species) {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.species = :species", Pet.class)
            .setParameter("species", species)
            .getResultList();
    }

    @GetMapping("/search")
    public List<Pet> searchPets(@RequestParam String name) {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.name LIKE :name", Pet.class)
            .setParameter("name", "%" + name + "%")
            .getResultList();
    }

    @GetMapping("/dogs")
    public List<Pet> getDogs() {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.species = :species", Pet.class)
            .setParameter("species", "Dog")
            .getResultList();
    }

    @GetMapping("/old-pets")
    public List<Pet> getOldPets() {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.age > :age", Pet.class)
            .setParameter("age", 2.0f)
            .getResultList();
    }

    @GetMapping("/{petId}/activities")
    public List<Activity> getActivitiesByPet(@PathVariable Integer petId) {
        return em.createQuery(
            "SELECT a FROM Activity a WHERE a.pet.id = :petId", Activity.class)
            .setParameter("petId", petId)
            .getResultList();
    }

    // Active Record: user.pets
    // @GetMapping("/user/{userId}/pets")
    // public List<Pet> getPetsByUser(@PathVariable Integer userId) {
    //     return petRepository.findByUserId(userId);
    // }

    @GetMapping("/search-fluff")
    public List<Pet> searchFluffPets(@RequestParam String name) {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.name LIKE :name", Pet.class)
            .setParameter("name", "%fluff%")
            .getResultList();
    }
}
