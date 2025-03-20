package com.vorofpie.teamcraft.controller;

import com.vorofpie.teamcraft.model.Technology;
import com.vorofpie.teamcraft.repository.TechnologyRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.List;

public class TechnologiesController {

    @FXML
    private TableView<Technology> technologiesTable;
    @FXML
    private TableColumn<Technology, Long> idColumn;
    @FXML
    private TableColumn<Technology, String> nameColumn;
    @FXML
    private TableColumn<Technology, String> categoryColumn;
    @FXML
    private TableColumn<Technology, Double> ratingColumn;
    @FXML
    private TableColumn<Technology, Boolean> isRequiredColumn;

    private final TechnologyRepository technologyRepository = new TechnologyRepository();

    @FXML
    public void initialize() {
        System.out.println("Контроллер инициализирован!");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("technologyId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        isRequiredColumn.setCellValueFactory(new PropertyValueFactory<>("isRequired"));

        loadTechnologies();
    }

    private void loadTechnologies() {
        List<Technology> technologies = technologyRepository.getAllTechnologies();
        ObservableList<Technology> technologyObservableList = FXCollections.observableArrayList(technologies);
        technologiesTable.setItems(technologyObservableList);
    }

    @FXML
    private void handleAddTechnology() {
        // Создаем новое диалоговое окно для ввода данных
        Dialog<Technology> dialog = new Dialog<>();
        dialog.setTitle("Добавить технологию");

        // Создаем поля ввода
        TextField nameField = new TextField();
        nameField.setPromptText("Название");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Категория");
        TextField ratingField = new TextField();
        ratingField.setPromptText("Рейтинг");
        CheckBox isRequiredCheckBox = new CheckBox("Обязательная");

        // Устанавливаем кнопку OK и обработчик
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButtonType);

        // Добавляем поля на диалоговое окно
        dialog.getDialogPane().setContent(new GridPane());
        GridPane grid = (GridPane) dialog.getDialogPane().getContent();
        grid.add(new Label("Название:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Категория:"), 0, 1);
        grid.add(categoryField, 1, 1);
        grid.add(new Label("Рейтинг:"), 0, 2);
        grid.add(ratingField, 1, 2);
        grid.add(isRequiredCheckBox, 0, 3, 2, 1); // Объединяем ячейки для чекбокса

        // Когда пользователь нажимает OK, сохраняем данные
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Technology technology = new Technology();
                technology.setName(nameField.getText());
                technology.setCategory(categoryField.getText());
                technology.setRating(Double.parseDouble(ratingField.getText()));
                technology.setIsRequired(isRequiredCheckBox.isSelected());
                return technology;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(technology -> {
            // Сохраняем технологию в базе данных
            technologyRepository.saveTechnology(technology);
            loadTechnologies(); // Обновляем список технологий
        });
    }
}
