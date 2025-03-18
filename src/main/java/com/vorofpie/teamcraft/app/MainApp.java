package com.vorofpie.teamcraft.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загрузка FXML файла
        Parent root = FXMLLoader.load(getClass().getResource("/com/vorofpie/teamcraft/fxml/main.fxml"));


        // Создание сцены
        Scene scene = new Scene(root, 1480, 720);

        // Настройка главного окна
        primaryStage.setTitle("Программное обеспечение для формирования групп программистов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}