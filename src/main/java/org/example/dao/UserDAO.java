package org.example.dao;

import org.example.entities.User;
import org.example.entities.Address;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private EntityManager em = emf.createEntityManager();

    public void saveUser(User user) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
            logger.info("User added: {}", user);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Error saving user: {}", e.getMessage());
        }
    }
    public User findUserByUsername(String username) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            logger.info("User found by username: {}", user);
            return user;
        } catch (NoResultException nre) {
            logger.warn("No user found with username: {}", username);
            return null;
        }
    }

    public User findUserById(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            logger.info("User found: {}", user);
        } else {
            logger.warn("User with id {} not found", id);
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        logger.info("Total users found: {}", users.size());
        for (User user : users) {
            logger.info(user.toString());
        }
        return users;
    }
    public void updateUser(User user) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(user);
            transaction.commit();
            logger.info("Updated user: {}", user);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating user: {}", e.getMessage());
        }
    }

    public void updateUserAddress(Long userId, Address newAddress) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setAddress(newAddress);
                em.merge(user);
                logger.info("Updated user address: {}", user);
            } else {
                logger.warn("User with id {} not found for update", userId);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Error updating user address: {}", e.getMessage());
        }
    }

    public void removeUserAddress(Long userId) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setAddress(null);
                em.merge(user);
                logger.info("Removed address from user: {}", user);
            } else {
                logger.warn("User with id {} not found for address removal", userId);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Error removing user address: {}", e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                logger.info("Deleted user: {}", user);
            } else {
                logger.warn("User with id {} not found for deletion", id);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Error deleting user: {}", e.getMessage());
        }
    }

    public void close() {
        em.close();
        emf.close();
        logger.info("EntityManager closed.");
    }
}
