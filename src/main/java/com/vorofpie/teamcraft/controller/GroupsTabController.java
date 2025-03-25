package com.vorofpie.teamcraft.controller;

import com.vorofpie.teamcraft.model.*;
import com.vorofpie.teamcraft.repository.ProgrammerRepository;
import com.vorofpie.teamcraft.repository.ProjectRepository;
import com.vorofpie.teamcraft.repository.TechnologyRepository;
import com.vorofpie.teamcraft.repository.ThresholdRepository;
import com.vorofpie.teamcraft.service.GroupOptimizationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GroupsTabController {

    private final ProgrammerRepository programmerRepository = new ProgrammerRepository();
    private final ProjectRepository projectRepository = new ProjectRepository();
    private final TechnologyRepository technologyRepository = new TechnologyRepository();
    private final ThresholdRepository thresholdRepository = new ThresholdRepository();
    private final GroupOptimizationService optimizationService = new GroupOptimizationService(programmerRepository, projectRepository, thresholdRepository);
    private final ObservableList<String> selectedTechnologies = FXCollections.observableArrayList();
    private final Map<String, Double> minLevels = new HashMap<>(); // Минимальные уровни для технологий
    private final Map<String, Double> groupLevels = new HashMap<>(); // Уровни группы для технологий
    @FXML
    private TextField groupNameField;
    @FXML
    private TextField projectNameField;
    @FXML
    private Spinner<Integer> groupSizeSpinner;
    @FXML
    private ListView<String> selectedTechnologiesList;
    @FXML
    private TableView<Programmer> programmersTable;
    @FXML
    private TableColumn<Programmer, String> nameColumn;
    @FXML
    private TableColumn<Programmer, Integer> experienceColumn;

    @FXML
    private TableColumn<Programmer, String> skillsColumn;

    @FXML
    public void initialize() {
        // Инициализация Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        groupSizeSpinner.setValueFactory(valueFactory);

        // Инициализация списка выбранных технологий
        selectedTechnologiesList.setItems(selectedTechnologies);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        experienceColumn.setCellValueFactory(cellData -> cellData.getValue().experienceYearsProperty().asObject());
        skillsColumn.setCellValueFactory(cellData -> {
            Programmer programmer = cellData.getValue();
            return new SimpleStringProperty(String.join(", ", programmer.getSkillsInfo()));
        });
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

        // Загрузка технологий из базы данных
        List<Technology> technologies = technologyRepository.getAllTechnologies();
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

            // Сохраняем минимальные уровни и уровни группы
            minLevels.put(techName, minLevel);
            groupLevels.put(techName, groupLevel);

            // Добавление технологии в список
            selectedTechnologies.add(techName + " (Минимальный уровень: " + minLevel + ", Уровень группы: " + groupLevel + ")");
        });
    }

    @FXML
    private void createGroup() {
        String projectName = projectNameField.getText();
        int groupSize = groupSizeSpinner.getValue();

        if (projectName.isEmpty()) {
            showAlert("Ошибка", "Название проекта не может быть пустым");
            return;
        }

        // Создание или поиск проекта
        Project project = projectRepository.getProjectByName(projectName);
        if (project == null) {
            project = new Project();
            project.setProjectName(projectName);
            project.setStartDate(LocalDate.now());
            project.setEndDate(LocalDate.now().plusMonths(6));
            projectRepository.save(project);
        }

        // Сохранение порогов (Threshold) в БД
        saveThresholdsToDatabase(project);

        // Оптимизация одной группы
        int populationSize = groupSize * 3; // Увеличиваем размер популяции для лучшего поиска
        Group bestGroup = optimizationService.optimizeGroup(project, groupSize, populationSize);

        // Отображение в таблице
        displayGroupInTable(bestGroup);

        // Очистка полей
        groupNameField.clear();
        projectNameField.clear();
        groupSizeSpinner.getValueFactory().setValue(1);
        selectedTechnologies.clear();
    }

    private void saveThresholdsToDatabase(Project project) {
        List<Technology> technologies = technologyRepository.getAllTechnologies();

        for (Technology tech : technologies) {
            if (minLevels.containsKey(tech.getName())) {
                Threshold threshold = new Threshold();
                threshold.setProject(project);
                threshold.setTechnology(tech);
                threshold.setMinLevel(minLevels.get(tech.getName()));
                threshold.setMinGroupLevel(groupLevels.get(tech.getName()));

                thresholdRepository.save(threshold);
            }
        }
    }

    private void displayGroupInTable(Group group) {
        programmersTable.getItems().clear();
        programmersTable.getItems().addAll(group.getProgrammers());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
