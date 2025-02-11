package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.GuestBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.GuestTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GuestsController implements Initializable {
    GuestBO guestBO = (GuestBO) BOFactory.getInstance();

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<GuestTM, String> tColEmail;

    @FXML
    private TableColumn<GuestTM, String> tColGuestId;

    @FXML
    private TableColumn<GuestTM, String> tColName;

    @FXML
    private TableColumn<GuestTM, Integer> tColPhone;

    @FXML
    private TableView<GuestTM> tblGuest;

    @FXML
    void onClickAdd(ActionEvent event) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AddGuestView.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Guest");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons8-add-48.png")));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
        loadTable();
    }

    @FXML
    void onClickDelete(ActionEvent event) {
        GuestTM guest = tblGuest.getSelectionModel().getSelectedItem();

        if (guest == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a guest!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this guest?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = guestBO.delete(guest.getGuestId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The guest is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the guest!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    @FXML
    void onClickRow(MouseEvent event) {
        btnUpdate.setDisable(false);
        btnDel.setDisable(false);
    }

    @FXML
    void onClickUpdate(ActionEvent event) {
        GuestTM guest = tblGuest.getSelectionModel().getSelectedItem();

        if (guest == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a guest!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateGuestView.fxml"));
            Parent load = loader.load();

            UpdateGuestViewController updateGuestViewController = loader.getController();
            updateGuestViewController.setGuest(guest);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Update Guest");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons8-update-50.png")));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load UI!");
            e.printStackTrace();
        }
        loadTable();
    }

    public void loadTable() {
        try {
            ArrayList<GuestDTO> guestDTOS = guestBO.getAll();

            ObservableList<GuestTM> guestTMS = FXCollections.observableArrayList();

            for (GuestDTO guestDTO : guestDTOS) {
                GuestTM guestTM = new GuestTM(
                        guestDTO.getGuestId(),
                        guestDTO.getName(),
                        guestDTO.getEmail(),
                        guestDTO.getPhoneNo()
                );
                guestTMS.add(guestTM);
            }

            tblGuest.setItems(guestTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        tColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tColPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        loadTable();
        btnUpdate.setDisable(true);
        btnDel.setDisable(true);
    }

}

