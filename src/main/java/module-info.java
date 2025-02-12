module lk.ijse.gdse71.rubyhallwithlayeredarchitecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires net.sf.jasperreports.core;
    requires java.mail;

//    requires javafx.controls;
//    requires javafx.fxml;
//    requires com.jfoenix;
//    requires java.sql;
//    requires mysql.connector.j;

//    opens lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto to javafx.base;
//    opens lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller to javafx.fxml;
//    exports lk.ijse.gdse71.rubyhallwithlayeredarchitecture;

    opens lk.ijse.gdse71.rubyhallwithlayeredarchitecture to javafx.fxml;
    opens lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller to javafx.fxml;
    opens lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm to javafx.base;

    exports lk.ijse.gdse71.rubyhallwithlayeredarchitecture;
    exports lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;
}