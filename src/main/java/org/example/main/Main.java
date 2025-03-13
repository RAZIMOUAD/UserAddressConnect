package org.example.main;

import org.example.dao.UserDAO;
import org.example.dao.DepartmentDAO;
import org.example.entities.User;
import org.example.entities.Address;
import org.example.entities.Department;
import org.example.entities.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialisation des DAO et du scanner
        UserDAO userDAO = new UserDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        logger.info("=== WELCOME TO USERADDRESSCONNECT PROJECT ===");

        while (running) {
            displayMainMenu();
            System.out.print("Please select an option: ");
            int option = getIntInput(scanner);
            switch (option) {
                case 1 -> addUserWithoutAddress(userDAO, scanner);
                case 2 -> addUserWithAddress(userDAO, scanner);
                case 3 -> listAllUsers(userDAO);
                case 4 -> updateUserAddress(userDAO, scanner);
                case 5 -> removeUserAddress(userDAO, scanner);
                case 6 -> deleteUser(userDAO, scanner);
                case 7 -> searchUser(userDAO, scanner);
                case 8 -> createDepartment(departmentDAO, scanner);
                case 9 -> assignUserToDepartment(userDAO, departmentDAO, scanner);
                case 10 -> listDepartmentUsers(departmentDAO, scanner);
                case 11 -> addCourseToUser(userDAO, scanner);
                case 12 -> removeCourseFromUser(userDAO, scanner);
                case 13 -> listUserCourses(userDAO, scanner);
                case 14 -> {
                    running = false;
                    System.out.println("Exiting the application. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        // Fermeture propre des ressources
        userDAO.close();
        departmentDAO.close();
        scanner.close();
        logger.info("=== APPLICATION TERMINATED ===");
    }

    // Affichage du menu principal
    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add user without address");
        System.out.println("2. Add user with address");
        System.out.println("3. List all users");
        System.out.println("4. Update user's address");
        System.out.println("5. Remove user's address");
        System.out.println("6. Delete user");
        System.out.println("7. Search user");
        System.out.println("8. Create department");
        System.out.println("9. Assign user to department");
        System.out.println("10. List department's users");
        System.out.println("11. Add course to user");
        System.out.println("12. Remove course from user");
        System.out.println("13. List user's courses");
        System.out.println("14. Quit");
    }

    // Méthode utilitaire pour lire un entier
    private static int getIntInput(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please try again.");
            return -1;
        }
    }

    // Méthode utilitaire pour lire un Long
    private static Long getLongInput(Scanner scanner) {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please try again.");
            return null;
        }
    }

    // ----------------- Gestion des utilisateurs (User) -----------------

    private static void addUserWithoutAddress(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        User user = new User(username, email);
        userDAO.saveUser(user);
        System.out.println("User added successfully!");
    }

    private static void addUserWithAddress(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter street: ");
        String street = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter state: ");
        String state = scanner.nextLine();
        Address address = new Address(street, city, state);
        User user = new User(username, email);
        user.setAddress(address);
        userDAO.saveUser(user);
        System.out.println("User with address added successfully!");
    }

    private static void listAllUsers(UserDAO userDAO) {
        System.out.println("\n--- List of Users ---");
        var users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUserAddress(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter user ID to update address: ");
        Long id = getLongInput(scanner);
        if (id == null) return;
        System.out.print("Enter new street: ");
        String street = scanner.nextLine();
        System.out.print("Enter new city: ");
        String city = scanner.nextLine();
        System.out.print("Enter new state: ");
        String state = scanner.nextLine();
        Address newAddress = new Address(street, city, state);
        userDAO.updateUserAddress(id, newAddress);
        System.out.println("User's address updated.");
    }

    private static void removeUserAddress(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter user ID to remove address: ");
        Long id = getLongInput(scanner);
        if (id == null) return;
        userDAO.removeUserAddress(id);
        System.out.println("User's address removed.");
    }

    private static void deleteUser(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        Long id = getLongInput(scanner);
        if (id == null) return;
        userDAO.deleteUser(id);
        System.out.println("User deleted.");
    }

    private static void searchUser(UserDAO userDAO, Scanner scanner) {
        System.out.println("Search by: 1. ID  2. Username");
        System.out.print("Your choice: ");
        int mode = getIntInput(scanner);
        switch (mode) {
            case 1 -> {
                System.out.print("Enter user ID: ");
                Long id = getLongInput(scanner);
                if (id == null) return;
                User user = userDAO.findUserById(id);
                if (user != null) {
                    System.out.println("User found: " + user);
                } else {
                    System.out.println("No user found with ID " + id);
                }
            }
            case 2 -> {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                User user = userDAO.findUserByUsername(username);
                if (user != null) {
                    System.out.println("User found: " + user);
                } else {
                    System.out.println("No user found with username " + username);
                }
            }
            default -> System.out.println("Invalid search mode.");
        }
    }

    // ----------------- Gestion des départements (Department) -----------------

    private static void createDepartment(DepartmentDAO departmentDAO, Scanner scanner) {
        System.out.print("Enter department name: ");
        String name = scanner.nextLine();
        Department dept = new Department(name);
        departmentDAO.saveDepartment(dept);
        System.out.println("Department created successfully!");
    }

    private static void assignUserToDepartment(UserDAO userDAO, DepartmentDAO departmentDAO, Scanner scanner) {
        System.out.print("Enter user ID: ");
        Long userId = getLongInput(scanner);
        if (userId == null) return;
        User user = userDAO.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.print("Enter department ID: ");
        Long deptId = getLongInput(scanner);
        if (deptId == null) return;
        Department dept = departmentDAO.findDepartmentById(deptId);
        if (dept == null) {
            System.out.println("Department not found.");
            return;
        }
        dept.addUser(user);
        departmentDAO.updateDepartment(dept);
        System.out.println("User assigned to department successfully!");
    }

    private static void listDepartmentUsers(DepartmentDAO departmentDAO, Scanner scanner) {
        System.out.print("Enter department ID: ");
        Long deptId = getLongInput(scanner);
        if (deptId == null) return;
        Department dept = departmentDAO.findDepartmentById(deptId);
        if (dept == null) {
            System.out.println("Department not found.");
            return;
        }
        System.out.println("Users in department " + dept.getName() + ":");
        if (dept.getUsers().isEmpty()) {
            System.out.println("No users in this department.");
        } else {
            dept.getUsers().forEach(System.out::println);
        }
    }

    // ----------------- Gestion des cours (Many-to-Many) -----------------

    private static void addCourseToUser(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter user ID to add a course: ");
        Long userId = getLongInput(scanner);
        if (userId == null) return;
        User user = userDAO.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        Course course = new Course(title);
        user.addCourse(course);
        userDAO.updateUser(user);
        System.out.println("Course added to user successfully!");
    }

    private static void removeCourseFromUser(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter user ID to remove a course: ");
        Long userId = getLongInput(scanner);
        if (userId == null) return;
        User user = userDAO.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        if (user.getCourses().isEmpty()) {
            System.out.println("User is not enrolled in any courses.");
            return;
        }
        System.out.println("User's courses:");
        user.getCourses().forEach(course -> System.out.println(course.getId() + ": " + course.getTitle()));
        System.out.print("Enter course ID to remove: ");
        Long courseId = getLongInput(scanner);
        if (courseId == null) return;
        Course courseToRemove = user.getCourses().stream()
                .filter(course -> course.getId() != null && course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
        if (courseToRemove == null) {
            System.out.println("Course not found.");
            return;
        }
        user.removeCourse(courseToRemove);
        userDAO.updateUser(user);
        System.out.println("Course removed from user successfully!");
    }

    private static void listUserCourses(UserDAO userDAO, Scanner scanner) {
        System.out.print("Enter user ID to list courses: ");
        Long userId = getLongInput(scanner);
        if (userId == null) return;
        User user = userDAO.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.println("Courses for user " + user.getUsername() + ":");
        if (user.getCourses().isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            user.getCourses().forEach(course -> System.out.println(course.getId() + ": " + course.getTitle()));
        }
    }
}
