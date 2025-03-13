package org.example.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // Relation Many-to-Many bidirectionnelle : un cours a plusieurs étudiants
    @ManyToMany(mappedBy = "courses")
    private List<User> users = new ArrayList<>();

    // Constructeurs
    public Course() {}

    public Course(String title) {
        this.title = title;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Méthode utilitaire pour ajouter un étudiant
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.getCourses().add(this); // maintient la cohérence bidirectionnelle
        }
    }

    // Méthode utilitaire pour retirer un étudiant
    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            user.getCourses().remove(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Course[id=%d, title='%s']", id, title);
    }
}
