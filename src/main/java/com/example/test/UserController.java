package com.example.test;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import com.example.test.model.User;
import com.example.test.model.Pet;
import com.example.test.model.Role;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private EntityManager em;

    @GetMapping
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = em.find(User.class, id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody User user) {
        em.persist(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User existing = em.find(User.class, id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        return ResponseEntity.ok(em.merge(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        User user = em.find(User.class, id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        em.remove(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        List<User> users = em.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class)
            .setParameter("email", email)
            .getResultList();
        
        return users.isEmpty() ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok(users.get(0));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        List<User> users = em.createQuery(
            "SELECT u FROM User u WHERE u.username = :username", User.class)
            .setParameter("username", username)
            .getResultList();
        
        return users.isEmpty() ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok(users.get(0));
    }

    @GetMapping("/{userId}/pets")
    public List<Pet> getUserPets(@PathVariable Integer userId) {
        return em.createQuery(
            "SELECT p FROM Pet p WHERE p.user_id = :userId", Pet.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    @GetMapping("/{userId}/roles")
    public Set<Role> getUserRoles(@PathVariable Integer userId) {
        User user = em.find(User.class, userId);
        return user != null ? user.getRoles() : null;
    }
}
