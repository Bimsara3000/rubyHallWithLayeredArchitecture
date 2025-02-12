package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private AnchorPane anchBlank;

    @FXML
    private AnchorPane anchLeft;

    @FXML
    private AnchorPane anchMainLayout;

    @FXML
    private Button btnGuests;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnReservations;

    @FXML
    private Button btnRooms;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnPayments;

    @FXML
    private VBox vBoxMainLayout;

    @FXML
    void clickBtnGuests(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Guests.fxml");
    }

    @FXML
    void clickBtnReports(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Reports.fxml");
    }

    @FXML
    void clickBtnReservations(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Reservations.fxml");
    }

    @FXML
    void clickBtnRooms(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Rooms.fxml");
    }

    @FXML
    void clickBtnUsers(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Advanced.fxml");
    }

    @FXML
    void clickBtnPayments(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Payments.fxml");
    }

    @FXML
    void onClickLogout(ActionEvent event) {
        try {
            anchMainLayout.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/Login.fxml"));

            anchMainLayout.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    private void disableButtons() {
        switch (LoginController.jobRole) {
            case "Accountant": btnReservations.setDisable(true); btnReports.setDisable(true); break;
            case "Administrator": btnPayments.setDisable(true); btnReports.setDisable(true); break;
            case "Receptionist": btnReports.setDisable(true); btnPayments.setDisable(true); btnRooms.setDisable(true);
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            anchBlank.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(anchBlank.widthProperty());
            load.prefHeightProperty().bind(anchBlank.heightProperty());

            anchBlank.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Platform.runLater(() -> {
//            anchMainLayout.setTopAnchor(anchBlank, 0.0);
//            anchMainLayout.setBottomAnchor(anchBlank, 0.0);
//            anchMainLayout.setRightAnchor(anchBlank, 0.0);
//            anchMainLayout.setTopAnchor(anchLeft, 0.0);
//            anchMainLayout.setBottomAnchor(anchLeft, 0.0);
//            anchMainLayout.setLeftAnchor(anchLeft, 0.0);
//            anchLeft.setTopAnchor(vBoxMainLayout, 0.0);
//            anchLeft.setBottomAnchor(vBoxMainLayout, 0.0);
//            anchLeft.setLeftAnchor(vBoxMainLayout, 0.0);
//            anchLeft.setRightAnchor(vBoxMainLayout, 750.0);
//        });
        disableButtons();
    }
}
