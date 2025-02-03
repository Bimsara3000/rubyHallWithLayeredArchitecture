package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdvancedController implements Initializable {

    @FXML
    private Button btnFacilities;

    @FXML
    private Button btnFloors;

    @FXML
    private Button btnJobRoles;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnPaymentTypes;

    @FXML
    private Button btnPriceFluctuations;

    @FXML
    private Button btnServices;

    @FXML
    private Button btnRoomTypes;

    @FXML
    private Button btnUsers;

    @FXML
    void onClickFacilities(ActionEvent event) {
        navigateTo("/view/FacilityView.fxml", "Facilities", "/images/Hospitality.jpg");
    }

    @FXML
    void onClickFloors(ActionEvent event) {
        navigateTo("/view/FloorView.fxml", "Floors", "/images/B49-2.jpg");
    }

    @FXML
    void onClickJobRoles(ActionEvent event) {
        navigateTo("/view/JobRoleView.fxml", "Job Roles", "/images/jobRole.png");
    }

    @FXML
    void onClickPackages(ActionEvent event) {
        navigateTo("/view/PackageView.fxml", "Packages", "/images/pacages.jpg");
    }

    @FXML
    void onClickPaymentTypes(ActionEvent event) {
        navigateTo("/view/PaymentTypeView.fxml", "Payment Types", "/images/paymentTypes.jpg");
    }

    @FXML
    void onClickPriceFluctuations(ActionEvent event) {
        navigateTo("/view/PriceFlucView.fxml", "Price Fluctuations", "/images/chart-graph-business-illustration_1134986-18347.jpg");
    }

    @FXML
    void onClickRoomTypes(ActionEvent event) {
        navigateTo("/view/RoomTypeView.fxml", "Room Types", "/images/room.jpg");
    }

    @FXML
    void onClickServices(ActionEvent event) {
        navigateTo("/view/ServiceView.fxml", "Services", "/images/istockphoto-1134867704-612x612.jpg");
    }

    @FXML
    void onClickUsers(ActionEvent event) {
        navigateTo("/view/UserView.fxml", "Users", "/images/user.png");
    }

    public void navigateTo(String fxmlPath, String title, String imagePath) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle(title);
            stage.getIcons().add(new Image(getClass().getResourceAsStream(imagePath))); //"/images/mail_icon.png"

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUsers.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    private void disableButtons() {
        switch (LoginController.jobRole) {
            case "Accountant": btnUsers.setDisable(true); break;
            case "Administrator": btnPriceFluctuations.setDisable(true); btnUsers.setDisable(true); break;
            case "Receptionist": btnPriceFluctuations.setDisable(true); btnUsers.setDisable(true); break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableButtons();
    }
}
