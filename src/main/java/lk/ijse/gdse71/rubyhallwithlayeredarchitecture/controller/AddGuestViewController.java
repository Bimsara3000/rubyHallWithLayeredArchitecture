package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.GuestBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddGuestViewController implements Initializable {
    GuestBO guestBO = (GuestBO) BOFactory.getInstance();

    @FXML
    private Label lblGuestId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    void onClickAdd(ActionEvent event) {
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
                boolean isSaved = guestBO.save(guestDTO);

                if (isSaved) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Guest saved!").show();
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
        refreshPage();
    }

    public void loadNextGuestId() {
        try {
            String nextGuestId = guestBO.getNextId();
            lblGuestId.setText(nextGuestId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNextGuestId();
    }

    private void refreshPage() {
        loadNextGuestId();
        txtName.setText("");
        txtEmail.setText("");
        txtPhoneNo.setText("");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: #7367F0;");
    }

}
