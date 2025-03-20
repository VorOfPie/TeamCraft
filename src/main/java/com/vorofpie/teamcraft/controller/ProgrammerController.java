package com.vorofpie.teamcraft.controller;

import com.vorofpie.teamcraft.model.Programmer;
import com.vorofpie.teamcraft.repository.ProgrammerRepository;
import com.vorofpie.teamcraft.util.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.w3c.dom.ls.LSOutput;

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

    // Колонка для отображения уровня навыков
    @FXML
    private TableColumn<Programmer, String> skillsColumn;

    @FXML
    private Button addProgrammerButton; // Кнопка добавления

    private ProgrammerRepository programmerRepository;

    @FXML
    private void initialize() {
        // Инициализация колонок таблицы
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        experienceYearsColumn.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
        educationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

        // Инициализация колонки для отображения навыков
        skillsColumn.setCellValueFactory(cellData -> {
            Programmer programmer = cellData.getValue();
            return new SimpleStringProperty(String.join(", ", programmer.getSkillsInfo()));
        });

        // Инициализация репозитория и загрузка данных
        programmerRepository = new ProgrammerRepository(HibernateUtil.getSessionFactory());
        loadProgrammers();
    }

    private void loadProgrammers() {
        // Получаем список программистов из репозитория
        List<Programmer> programmers = programmerRepository.getAllProgrammers();
        ObservableList<Programmer> programmerObservableList = FXCollections.observableArrayList(programmers);
        programmersTable.setItems(programmerObservableList);
    }

    // Обработчик кнопки добавления программиста
    @FXML
    private void handleAddProgrammer() {
        // Создаем новое диалоговое окно для ввода данных
        Dialog<Programmer> dialog = new Dialog<>();
        dialog.setTitle("Добавить программиста");

        // Создаем поля ввода
        TextField nameField = new TextField();
        nameField.setPromptText("Имя");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField experienceField = new TextField();
        experienceField.setPromptText("Опыт (лет)");
        TextField educationField = new TextField();
        educationField.setPromptText("Образование");
        TextField companyField = new TextField();
        companyField.setPromptText("Компания");

        // Устанавливаем кнопку OK и обработчик
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButtonType);

        // Добавляем поля на диалоговое окно
        dialog.getDialogPane().setContent(new GridPane());
        GridPane grid = (GridPane) dialog.getDialogPane().getContent();
        grid.add(new Label("Имя:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Опыт:"), 0, 2);
        grid.add(experienceField, 1, 2);
        grid.add(new Label("Образование:"), 0, 3);
        grid.add(educationField, 1, 3);
        grid.add(new Label("Компания:"), 0, 4);
        grid.add(companyField, 1, 4);

        // Когда пользователь нажимает OK, сохраняем данные
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Programmer programmer = new Programmer();
                programmer.setName(nameField.getText());
                programmer.setEmail(emailField.getText());
                programmer.setExperienceYears(Integer.parseInt(experienceField.getText()));
                programmer.setEducation(educationField.getText());
                programmer.setCompany(companyField.getText());
                return programmer;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(programmer -> {
            // Сохраняем программиста в базе данных
            programmerRepository.saveProgrammer(programmer);
            loadProgrammers(); // Обновляем список программистов
        });
    }

}
