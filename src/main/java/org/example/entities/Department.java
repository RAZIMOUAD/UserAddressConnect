package org.example.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Relation One-to-Many bidirectionnelle : un département a plusieurs étudiants
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    // Constructeurs
    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Méthodes utilitaires pour maintenir la cohérence de la relation bidirectionnelle
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.setDepartment(this);
        }
    }

    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            user.setDepartment(null);
        }
    }

    @Override
    public String toString() {
        return String.format("Department[id=%d, name='%s']", id, name);
    }
}
