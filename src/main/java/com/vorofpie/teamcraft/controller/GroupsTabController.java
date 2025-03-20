package com.vorofpie.teamcraft.controller;

import com.vorofpie.teamcraft.model.Technology;
import com.vorofpie.teamcraft.repository.TechnologyRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class GroupsTabController {

    @FXML
    private TextField groupNameField;

    @FXML
    private TextField projectNameField;

    @FXML
    private Spinner<Integer> groupSizeSpinner;

    @FXML
    private ListView<String> selectedTechnologiesList;

    private ObservableList<String> selectedTechnologies = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Инициализация Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        groupSizeSpinner.setValueFactory(valueFactory);

        // Инициализация списка выбранных технологий
        selectedTechnologiesList.setItems(selectedTechnologies);
    }

    @FXML
    private void openTechnologyDialog() {
        // Создание диалогового окна
        Dialog<Pair<String, Pair<Double, Double>>> dialog = new Dialog<>();
        dialog.setTitle("Добавить технологию");
        dialog.setHeaderText("Выберите технологию и укажите минимальные уровни");

        // Установка кнопок OK и Cancel
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Создание ComboBox для выбора технологии
        ComboBox<String> technologyComboBox = new ComboBox<>();
        technologyComboBox.setPromptText("Выберите технологию");

        // Загрузка технологий из базы данных (пример)
        TechnologyRepository repository = new TechnologyRepository();
        List<Technology> technologies = repository.getAllTechnologies();
        for (Technology tech : technologies) {
            technologyComboBox.getItems().add(tech.getName());
        }

        // Поля для ввода минимального уровня и уровня группы
        TextField minLevelField = new TextField();
        minLevelField.setPromptText("Минимальный уровень");

        TextField groupLevelField = new TextField();
        groupLevelField.setPromptText("Минимальный уровень группы");

        // Добавление элементов в диалоговое окно
        dialog.getDialogPane().setContent(new VBox(10, technologyComboBox, minLevelField, groupLevelField));

        // Преобразование результата диалога
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String selectedTech = technologyComboBox.getValue();
                double minLevel = Double.parseDouble(minLevelField.getText());
                double groupLevel = Double.parseDouble(groupLevelField.getText());
                return new Pair<>(selectedTech, new Pair<>(minLevel, groupLevel));
            }
            return null;
        });

        // Отображение диалога и обработка результата
        Optional<Pair<String, Pair<Double, Double>>> result = dialog.showAndWait();
        result.ifPresent(techData -> {
            String techName = techData.getKey();
            double minLevel = techData.getValue().getKey();
            double groupLevel = techData.getValue().getValue();

            // Добавление технологии в список
            selectedTechnologies.add(techName + " (Минимальный уровень: " + minLevel + ", Уровень группы: " + groupLevel + ")");
        });
    }

    @FXML
    private void createGroup() {
        String groupName = groupNameField.getText();
        String projectName = projectNameField.getText();
        int groupSize = groupSizeSpinner.getValue();

        // Логика создания группы
        System.out.println("Группа создана:");
        System.out.println("Название группы: " + groupName);
        System.out.println("Название проекта: " + projectName);
        System.out.println("Количество человек: " + groupSize);
        System.out.println("Выбранные технологии: " + selectedTechnologies);

        // Очистка полей
        groupNameField.clear();
        projectNameField.clear();
        groupSizeSpinner.getValueFactory().setValue(1);
        selectedTechnologies.clear();
    }
}