module lk.ijse.gdse71.rubyhallwithlayeredarchitecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller to javafx.fxml;
    exports lk.ijse.gdse71.rubyhallwithlayeredarchitecture;
}