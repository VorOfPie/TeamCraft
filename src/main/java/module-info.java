module com.vorofpie.teamcraft {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vorofpie.teamcraft to javafx.fxml;
    exports com.vorofpie.teamcraft;
}