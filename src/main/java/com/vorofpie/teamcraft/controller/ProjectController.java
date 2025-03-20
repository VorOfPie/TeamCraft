package com.vorofpie.teamcraft.controller;

import com.vorofpie.teamcraft.model.Programmer;
import com.vorofpie.teamcraft.model.Project;
import com.vorofpie.teamcraft.model.Group;
import com.vorofpie.teamcraft.repository.ProgrammerRepository;
import com.vorofpie.teamcraft.repository.ProjectRepository;
import com.vorofpie.teamcraft.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectController {

    @FXML
    private ComboBox<String> projectComboBox;

    @FXML
    private ComboBox<Group> groupComboBox;

    @FXML
    private TableView<Programmer> programmerTableView;

    private final ProjectRepository projectRepository = new ProjectRepository(); // Инициализация репозитория
    private final ProgrammerRepository programmerRepository = new ProgrammerRepository(HibernateUtil.getSessionFactory());

    private ObservableList<Programmer> programmers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadProjects();
        setupGroupComboBoxConverter();
    }

    private void loadProjects() {
        List<Project> projects = projectRepository.getAllProjects(); // Получение всех проектов из репозитория
        ObservableList<String> projectObservableList = FXCollections.observableArrayList(
                projects.stream()
                        .map(Project::getProjectName)
                        .collect(Collectors.toList())
        );
        projectComboBox.setItems(projectObservableList);
    }

    private void setupGroupComboBoxConverter() {
        // Настройка StringConverter для groupComboBox
        groupComboBox.setConverter(new StringConverter<Group>() {
            @Override
            public String toString(Group group) {
                if (group == null) {
                    return "";
                }
                return group.getGroupName(); // Отображаем только имя группы
            }

            @Override
            public Group fromString(String string) {
                // Этот метод используется, если нужно преобразовать строку обратно в объект Group
                // В данном случае он не нужен, но его нужно реализовать
                return null;
            }
        });
    }

    @FXML
    private void handleProjectSelection() {
        String selectedProjectName = projectComboBox.getValue();
        if (selectedProjectName != null) {
            Project selectedProject = projectRepository.getProjectByName(selectedProjectName);
            ObservableList<Group> groups = FXCollections.observableArrayList(selectedProject.getGroups());
            groupComboBox.setItems(groups);
        }
    }

    @FXML
    private void handleGroupSelection() {
        Group selectedGroup = groupComboBox.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            // Загрузка программистов для выбранной группы
            List<Programmer> programmers = programmerRepository.getProgrammersByGroupId(selectedGroup.getGroupId());
            programmerTableView.getItems().setAll(programmers); // Обновляем TableView
        }
    }
}