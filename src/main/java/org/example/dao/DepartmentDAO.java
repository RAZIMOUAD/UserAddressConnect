package org.example.dao;

import org.example.entities.Department;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class DepartmentDAO {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentDAO.class);
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
    private EntityManager em = emf.createEntityManager();

    /**
     * Sauvegarde un département dans la base de données.
     */
    public void saveDepartment(Department department) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(department);
            transaction.commit();
            logger.info("Department saved: {}", department);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error saving department: {}", e.getMessage());
        }
    }

    /**
     * Recherche un département par son identifiant.
     */
    public Department findDepartmentById(Long id) {
        Department department = em.find(Department.class, id);
        if (department != null) {
            logger.info("Department found: {}", department);
        } else {
            logger.warn("Department with id {} not found", id);
        }
        return department;
    }

    /**
     * Récupère tous les départements.
     */
    public List<Department> getAllDepartments() {
        List<Department> departments = em.createQuery("SELECT d FROM Department d", Department.class)
                .getResultList();
        logger.info("Total departments found: {}", departments.size());
        return departments;
    }

    /**
     * Met à jour un département existant.
     */
    public void updateDepartment(Department department) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(department);
            transaction.commit();
            logger.info("Department updated: {}", department);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating department: {}", e.getMessage());
        }
    }

    /**
     * Supprime un département par son identifiant.
     */
    public void deleteDepartment(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Department department = em.find(Department.class, id);
            if (department != null) {
                em.remove(department);
                logger.info("Department deleted: {}", department);
            } else {
                logger.warn("Department with id {} not found for deletion", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error deleting department: {}", e.getMessage());
        }
    }

    /**
     * Ferme l'EntityManager et l'EntityManagerFactory.
     */
    public void close() {
        em.close();
        emf.close();
        logger.info("EntityManager closed for DepartmentDAO.");
    }
}
