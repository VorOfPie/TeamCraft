package com.vorofpie.teamcraft.repository;

import com.vorofpie.teamcraft.model.Project;
import com.vorofpie.teamcraft.model.Technology;
import com.vorofpie.teamcraft.model.Threshold;
import com.vorofpie.teamcraft.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ThresholdRepository {

    public void save(Threshold threshold) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(threshold);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Threshold> findByProject(Project project) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT t FROM Threshold t WHERE t.project = :project", Threshold.class)
                    .setParameter("project", project)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Threshold findByProjectAndTechnology(Project project, Technology technology) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT t FROM Threshold t WHERE t.project = :project AND t.technology = :technology", Threshold.class)
                    .setParameter("project", project)
                    .setParameter("technology", technology)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }
}
