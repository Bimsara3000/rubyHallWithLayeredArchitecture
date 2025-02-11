package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddReservationViewController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnChooseRoom;

    @FXML
    private Button btnClear;

    @FXML
    private ComboBox<?> cmbPackage;

    @FXML
    private Label lblResId;

    @FXML
    private Label lblRoomId;

    @FXML
    private Label lblUserName;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtGuestCount;

    @FXML
    private TextField txtGuestName;

    @FXML
    private TextField txtStartDate;

    @FXML
    void onActionCmbPackage(ActionEvent event) {

    }

    @FXML
    void onClickAdd(ActionEvent event) {

    }

    @FXML
    void onClickChoosePackage(ActionEvent event) {

    }

    String isDate = "\\d{4}-\\d{2}-\\d{2}";

    @FXML
    void onClickChooseRoom(ActionEvent event) {
        if (txtStartDate.getText().isEmpty() || txtEndDate.getText().isEmpty() || !txtEndDate.getText().matches(isDate) || !txtStartDate.getText().matches(isDate)) {
            new Alert(Alert.AlertType.ERROR, "An error with start or end date!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChooseRoom.fxml"));
            Parent load = loader.load();

            ChooseRoomController chooseRoomController = loader.getController();

            chooseRoomController.setStartDate(txtStartDate.getText());
            chooseRoomController.setEndDate(txtEndDate.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Choose Room");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons8-room-48.png")));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnChooseRoom.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }

    }

    @FXML
    void onClickClear(ActionEvent event) {

    }

    ReservationModel reservationModel = new ReservationModel();
    UserModel userModel = new UserModel();

    public void loadNextResId() {
        try {
            String nextResId = reservationModel.getNextReservationId();
            lblResId.setText(nextResId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNextResId();
        try {
            lblUserName.setText(userModel.getUserName(LoginController.userId));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
    }
}
