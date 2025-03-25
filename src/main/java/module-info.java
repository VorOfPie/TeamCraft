module com.vorofpie.teamcraft {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires java.naming;
    requires jakarta.transaction;
    opens com.vorofpie.teamcraft.model to org.hibernate.orm.core, javafx.base;
    opens com.vorofpie.teamcraft.controller to javafx.fxml;
    exports com.vorofpie.teamcraft.app;
    opens com.vorofpie.teamcraft.app to javafx.fxml;
    exports com.vorofpie.teamcraft.controller;
}
