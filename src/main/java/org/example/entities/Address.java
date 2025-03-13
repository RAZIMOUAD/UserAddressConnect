package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    // Constructeur par défaut
    public Address() {}

    // Constructeur avec paramètres
    public Address(String street, String city, String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("Address[id=%d, street='%s', city='%s', state='%s']",
                id, street, city, state);
    }
}

