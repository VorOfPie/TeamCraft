package com.vorofpie.teamcraft.repository;

import com.vorofpie.teamcraft.model.Technology;
import com.vorofpie.teamcraft.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TechnologyRepository {

    public List<Technology> getAllTechnologies() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Technology> technologies = session.createQuery("FROM Technology", Technology.class).list();
            return technologies;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void saveTechnology(Technology technology) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(technology);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
