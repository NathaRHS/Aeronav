package com.example.app.repository;

import com.example.app.entity.TypeCarburant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TypeCarburantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TypeCarburant> findAll() {
        return entityManager.createQuery(
                "SELECT t FROM TypeCarburant t ORDER BY t.prioriteAllocation ASC, t.libelle ASC",
                TypeCarburant.class)
                .getResultList();
    }

    public TypeCarburant findByLibelle(String libelle) {
        if (libelle == null || libelle.trim().isEmpty()) {
            return null;
        }
        List<TypeCarburant> result = entityManager.createQuery(
                "SELECT t FROM TypeCarburant t WHERE LOWER(t.libelle) = LOWER(:libelle)",
                TypeCarburant.class)
                .setParameter("libelle", libelle.trim())
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
