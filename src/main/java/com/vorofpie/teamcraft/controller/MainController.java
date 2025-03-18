package com.vorofpie.teamcraft.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    private MenuItem saveMenuItem; // Элемент меню "Сохранить"

    @FXML
    private MenuItem loadMenuItem; // Элемент меню "Загрузить"

    @FXML
    private MenuItem exitMenuItem; // Элемент меню "Выход"

    @FXML
    private MenuItem aboutMenuItem; // Элемент меню "О программе"

    @FXML
    private TabPane tabPane; // Панель вкладок

    // Инициализация контроллера
    @FXML
    public void initialize() {
        // Обработка событий для меню
        saveMenuItem.setOnAction(event -> onSave());
        loadMenuItem.setOnAction(event -> onLoad());
        exitMenuItem.setOnAction(event -> onExit());
        aboutMenuItem.setOnAction(event -> onAbout());
    }

    // Обработчик для кнопки "Сохранить"
    private void onSave() {
        System.out.println("Сохранить данные...");
        // Здесь можно добавить логику сохранения данных
    }

    // Обработчик для кнопки "Загрузить"
    private void onLoad() {
        System.out.println("Загрузить данные...");
        // Здесь можно добавить логику загрузки данных
    }

    // Обработчик для кнопки "Выход"
    private void onExit() {
        System.out.println("Выход из приложения...");
        System.exit(0); // Закрытие приложения
    }

    // Обработчик для кнопки "О программе"
    private void onAbout() {
        System.out.println("О программе...");
        // Здесь можно показать диалоговое окно с информацией о программе
    }
}