package com.example.test.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
//import com.example.test.model.Activity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private Float age;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private java.util.Date create_at;

    @OneToMany(mappedBy = "pet")
    
    private List<Activity> activities;

    public void setId(Integer id)
    {
        this.id = id;
    }
    public Integer getId(){

        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(java.util.Date create_at) {
        this.create_at = create_at;
    }


}
