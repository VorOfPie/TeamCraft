package com.vorofpie.teamcraft.repository;

import com.vorofpie.teamcraft.model.Programmer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProgrammerRepository {

    private final SessionFactory sessionFactory;

    public ProgrammerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Programmer> getAllProgrammers() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<Programmer> query = session.createQuery("FROM Programmer", Programmer.class);
            List<Programmer> programmers = query.getResultList();
            session.getTransaction().commit();
            return programmers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
