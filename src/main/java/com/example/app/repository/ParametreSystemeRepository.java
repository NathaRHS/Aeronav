package com.example.app.repository;

import com.example.app.entity.ParametreSysteme;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ParametreSystemeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String findValueByKey(String cleParametre) {
        List<String> values = entityManager.createQuery(
                "SELECT p.valeurParametre FROM ParametreSysteme p WHERE p.cleParametre = :key",
                String.class)
                .setParameter("key", cleParametre)
                .setMaxResults(1)
                .getResultList();

        return values.isEmpty() ? null : values.get(0);
    }

    public double findDoubleValueByKey(String cleParametre, double defaultValue) {
        String value = findValueByKey(cleParametre);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
