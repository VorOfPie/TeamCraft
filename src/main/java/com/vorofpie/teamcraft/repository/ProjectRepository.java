package com.vorofpie.teamcraft.repository;

import com.vorofpie.teamcraft.model.Group;
import com.vorofpie.teamcraft.model.Project;
import com.vorofpie.teamcraft.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectRepository {

    public List<Project> getAllProjects() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Project", Project.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Возвращаем пустой список в случае ошибки
        }
    }

    public Project getProjectByName(String projectName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Fetch the project
            Project project = session.createQuery(
                            "SELECT p FROM Project p WHERE p.projectName = :projectName", Project.class)
                    .setParameter("projectName", projectName)
                    .uniqueResult();

            if (project != null) {
                // Fetch the groups for the project
                List<Group> groupList = session.createQuery(
                                "SELECT g FROM Group g WHERE g.project.projectId = :projectId", Group.class)
                        .setParameter("projectId", project.getProjectId())
                        .getResultList();

                // Convert List to Set and assign it to the project's groups
                Set<Group> groupSet = new HashSet<>(groupList);
                project.setGroups(groupSet);
            }

            return project;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(Project project) {
        Transaction transaction = null;  // Для обработки транзакции
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();  // Начинаем транзакцию

            session.persist(project);  // Сохраняем объект в базе данных

            transaction.commit();  // Завершаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }

}