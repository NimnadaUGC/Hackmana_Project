module org.example.hakmana {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires com.jfoenix;
    requires java.desktop;
    requires java.mail;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires jdk.httpserver;
    requires org.apache.logging.log4j.core;
    requires kernel;
    requires layout;

    opens org.example.hakmana.model.overviewTable to javafx.base;
    opens org.example.hakmana to javafx.fxml;
    exports org.example.hakmana;
    exports org.example.hakmana.model;
    opens org.example.hakmana.model to javafx.fxml;
    exports org.example.hakmana.view.component;
    opens org.example.hakmana.view.component to javafx.fxml;
    exports org.example.hakmana.view.scene;
    opens org.example.hakmana.view.scene to javafx.fxml;
    exports org.example.hakmana.view.dialogBoxes;
    opens org.example.hakmana.view.dialogBoxes to javafx.fxml;
    exports org.example.hakmana.model.mainDevices;
    opens org.example.hakmana.model.mainDevices to javafx.fxml;
    exports org.example.hakmana.model.userMngmnt;
    opens org.example.hakmana.model.userMngmnt to javafx.fxml;
    exports org.example.hakmana.model.otherDevices;
    opens org.example.hakmana.model.otherDevices to javafx.fxml;
    exports org.example.hakmana.model.noteHndling;
    opens org.example.hakmana.model.noteHndling to javafx.fxml;


}
