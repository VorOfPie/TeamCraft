package com.vorofpie.teamcraft.repository;

import com.vorofpie.teamcraft.model.Programmer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ProgrammerRepository {

    private final SessionFactory sessionFactory;

    public ProgrammerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Programmer> getAllProgrammers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT p FROM Programmer p " +
                                    "LEFT JOIN FETCH p.skillLevels sl " +
                                    "LEFT JOIN FETCH sl.technology", Programmer.class)
                    .list();
        }
    }

    public List<Programmer> getProgrammersByGroupId(Long groupId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT gm.programmer FROM GroupMember gm " +
                                    "WHERE gm.group.groupId = :groupId", Programmer.class)
                    .setParameter("groupId", groupId)
                    .list();
        }
    }

    public void saveProgrammer(Programmer programmer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(programmer); // Используем persist вместо save
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Логируем исключение
        }
    }
}