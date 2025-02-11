package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.GuestBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.GuestTM;
import lombok.Setter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateGuestViewController implements Initializable {
    GuestBO guestBO = (GuestBO) BOFactory.getInstance(BOFactory.BOType.USER);

    @FXML
    private Label lblGuestId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNo;

    @Setter
    private GuestTM guest;

    @FXML
    void onClickUpdate(ActionEvent event) {

        String guestId = lblGuestId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhoneNo.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone || phone.isEmpty()) {
            txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidEmail && isValidPhone && !phone.isEmpty()) {
            GuestDTO guestDTO = new GuestDTO(
                    guestId,
                    name,
                    email,
                    Integer.parseInt(phone)
            );

            try {
                boolean isUpdated = guestBO.update(guestDTO);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Guest saved!").show();
                    Stage stage  = (Stage) lblGuestId.getScene().getWindow();
                    stage.close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save guest!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    @FXML
    void onClickClear(ActionEvent event) {
        txtName.setText("");
        txtEmail.setText("");
        txtPhoneNo.setText("");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if(guest == null) {
                System.out.println("Null Guest");
                return;
            }
            lblGuestId.setText(guest.getGuestId());
            txtName.setText(guest.getName());
            txtEmail.setText(guest.getEmail());
            txtPhoneNo.setText(Integer.toString(guest.getPhoneNo()));
        });
    }
}
