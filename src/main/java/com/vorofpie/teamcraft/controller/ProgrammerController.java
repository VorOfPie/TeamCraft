package com.vorofpie.teamcraft.controller;

import com.vorofpie.teamcraft.model.Programmer;
import com.vorofpie.teamcraft.repository.ProgrammerRepository;
import com.vorofpie.teamcraft.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProgrammerController {

    @FXML
    private TableView<Programmer> programmersTable;

    @FXML
    private TableColumn<Programmer, String> nameColumn;
    @FXML
    private TableColumn<Programmer, String> emailColumn;
    @FXML
    private TableColumn<Programmer, Integer> experienceYearsColumn;
    @FXML
    private TableColumn<Programmer, String> educationColumn;
    @FXML
    private TableColumn<Programmer, String> companyColumn;

    private ProgrammerRepository programmerRepository;

    @FXML
    private void initialize() {
        // Инициализация колонок таблицы
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        experienceYearsColumn.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
        educationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

        // Инициализация репозитория и загрузка данных
        programmerRepository = new ProgrammerRepository(HibernateUtil.getSessionFactory());
        loadProgrammers();
    }

    private void loadProgrammers() {
        List<Programmer> programmers = programmerRepository.getAllProgrammers();
        ObservableList<Programmer> programmerObservableList = FXCollections.observableArrayList(programmers);
        programmersTable.setItems(programmerObservableList);
    }
}
