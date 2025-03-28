
---

# UserAddressConnect

**UserAddressConnect** est un projet Java JPA qui illustre une gestion complète des relations entre entités dans une base de données. Le projet intègre divers types de relations :  
- **One-to-One** : Association entre un étudiant et son adresse.  
- **Many-to-One** : Association entre un étudiant et un département.  
- **Many-to-Many** : Inscription des étudiants à plusieurs cours (et vice versa).  

Ce projet a été conçu pour démontrer les différentes stratégies de mapping en JPA et pour servir de base évolutive pour des applications complexes.

---

## Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Technologies Utilisées](#technologies-utilisées)
- [Structure du Projet](#structure-du-projet)
- [Installation et Exécution](#installation-et-exécution)
- [Utilisation](#utilisation)
---

## Fonctionnalités

- **Gestion des étudiants** : Création, lecture, mise à jour et suppression d'étudiants (CRUD).  
- **Relation One-to-One** : Chaque étudiant possède une adresse unique.  
- **Relation Many-to-One** : Chaque étudiant est associé à un département.  
- **Relation Many-to-Many** : Les étudiants peuvent s'inscrire à plusieurs cours et chaque cours peut être suivi par plusieurs étudiants.  
- **Interface en ligne de commande** : Un menu interactif permet de tester et d'exécuter toutes les fonctionnalités du projet.

---

## Technologies Utilisées

- **Java 22**  
- **JPA (Jakarta Persistence API)** avec EclipseLink  
- **MySQL** pour la base de données  
- **SLF4J & Logback** pour la gestion des logs  
- **Maven** pour la gestion des dépendances et de la compilation

---

## Structure du Projet

Le projet est organisé de manière modulaire pour une meilleure maintenabilité :

```
UserAddressConnect/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── org.example.entities/
│   │   │   │   ├── User.java         // Entité représentant l'étudiant
│   │   │   │   ├── Address.java      // Entité One-to-One avec User
│   │   │   │   ├── Department.java   // Entité Many-to-One, regroupant des étudiants
│   │   │   │   ├── Course.java       // Entité Many-to-Many, représentant les cours
│   │   │   ├── org.example.dao/
│   │   │   │   ├── UserDAO.java
│   │   │   │   └── DepartmentDAO.java
│   │   │   └── org.example.main/
│   │   │       └── Main.java         // Menu interactif et logique applicative
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml  // Configuration JPA
│   └── test/
│       └── java/ ... (tests unitaires, si nécessaire)
│
├── pom.xml                         // Fichier de configuration Maven
└── README.md                       // Ce fichier
```

---

## Installation et Exécution

### Prérequis

- **Java JDK 22** ou version supérieure
- **Maven 3.x**
- **MySQL** avec une base de données nommée par exemple `mydatabase`

### Étapes d'installation

1. **Cloner le dépôt :**

   ```bash
   git clone https://github.com/RAZIMOUAD/UserAddressConnect.git
   cd UserAddressConnect
   ```

2. **Configurer la base de données :**  
   Modifiez le fichier `persistence.xml` pour adapter l'URL, l'utilisateur et le mot de passe de la base de données.

3. **Compiler et exécuter le projet :**

   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="org.example.main.Main"
   ```

   Ou utilisez votre IDE préféré (par exemple IntelliJ IDEA) pour lancer la classe Main.

---

## Utilisation

Une fois l'application démarrée, vous verrez un menu interactif vous permettant de :

- Ajouter un étudiant avec ou sans adresse.
- Lister, rechercher, mettre à jour ou supprimer un étudiant.
- Créer un département et y assigner des étudiants.
- Gérer l'inscription aux cours via une relation many-to-many.

Les logs générés par SLF4J et Logback fournissent un suivi détaillé de chaque opération et facilitent le débogage.

Exemple d'utilisation via l'interface CLI :

```
--- MAIN MENU ---
1. Add student without address
2. Add student with address
3. List all students
4. Update student's address
5. Remove student's address
6. Delete student
7. Search student
8. Create department
9. Assign student to department
10. List department's students
11. Add course to student
12. Remove course from student
13. List student's courses
14. Quit
Please select an option: 2
```

Chaque option est conçue pour être claire et intuitive, rendant l'expérience utilisateur professionnelle et fluide.
---

