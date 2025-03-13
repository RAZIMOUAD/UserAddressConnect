package org.example.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    // Relation one-to-one unidirectionnelle vers Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    // Constructeur par défaut
    public User() {}

    // Constructeur sans adresse
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.getUsers().add(this);
        }
    }

    public void removeCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.getUsers().remove(this);
        }
    }
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Informations de l'étudiant ===\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nom d'utilisateur: ").append(username).append("\n");
        sb.append("Email: ").append(email).append("\n");

        // Affichage de la relation one-to-one avec Address
        sb.append("Adresse (One-to-One): ");
        sb.append(address != null ? address.toString() : "Aucune").append("\n");

        // Affichage de la relation many-to-one avec Department (si elle est implémentée)
        sb.append("Département (Many-to-One): ");
        sb.append(department != null ? department.getName() : "Aucun").append("\n");

        // Affichage de la relation many-to-many avec Courses
        sb.append("Cours (Many-to-Many): ");
        if (courses != null && !courses.isEmpty()) {
            courses.forEach(course -> sb.append(course.getTitle()).append(" ; "));
        } else {
            sb.append("Aucun");
        }
        sb.append("\n");


        // Commentaire sur le raisonnement derrière ces mappings
        sb.append("Raisonnement: Cet objet User illustre comment différentes stratégies de mapping en JPA peuvent être combinées :\n")
                .append("- One-to-One pour associer une adresse unique à chaque étudiant.\n")
                .append("- Many-to-One pour relier l'étudiant à un département (ou une filière).\n")
                .append("- Many-to-Many pour permettre à un étudiant de suivre plusieurs cours, et à un cours d'être suivi par plusieurs étudiants.\n")
                .append("Ce modèle permet une grande flexibilité et prépare l'évolution vers des associations plus complexes.\n");

        return sb.toString();
    }

}
