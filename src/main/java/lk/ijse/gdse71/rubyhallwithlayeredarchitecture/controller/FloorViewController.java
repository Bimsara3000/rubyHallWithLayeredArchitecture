package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FloorBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FloorDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.FloorTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class FloorViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblFloorId;

    @FXML
    private TableColumn<FloorTM, String> tColDesc;

    @FXML
    private TableColumn<FloorTM, String> tColFloorId;

    @FXML
    private TableView<FloorTM> tblFloor;

    @FXML
    private TextField txtDesc;

    FloorBO floorBO = (FloorBO) BOFactory.getInstance().getBO(BOFactory.BOType.FLOOR);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        FloorTM floorTM = tblFloor.getSelectionModel().getSelectedItem();

        if (floorTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this floor?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = floorBO.delete(floorTM.getFloorId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The floor is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the floor!").show();
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
    void btnSaveOnAction(ActionEvent event) {
        String desc = txtDesc.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!desc.isEmpty()) {
            try {
                FloorDTO floorDTO = new FloorDTO(
                        lblFloorId.getText(),
                        desc
                );

                boolean isSaved = floorBO.save(floorDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Floor saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Floor!").show();
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
    void btnUpdateOnAction(ActionEvent event) {
        FloorTM floorTM = tblFloor.getSelectionModel().getSelectedItem();

        if (floorTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type!").show();
            return;
        }

        String desc = txtDesc.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!desc.isEmpty()) {
            try {
                FloorDTO floorDTO = new FloorDTO(
                        lblFloorId.getText(),
                        desc
                );

                boolean isSaved = floorBO.update(floorDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Floor type updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Floor type!").show();
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
    void onClickTable(MouseEvent event) {
        FloorTM floorTM = tblFloor.getSelectionModel().getSelectedItem();

        lblFloorId.setText(floorTM.getFloorId());
        txtDesc.setText(floorTM.getDescription());

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

    public void loadNextFloorId() {
        try {
            String nextFloorId = floorBO.getNextId();
            lblFloorId.setText(nextFloorId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<FloorDTO> floorDTOS = floorBO.getAll();

            ObservableList<FloorTM> floorTMS = FXCollections.observableArrayList();

            for (FloorDTO floorDTO : floorDTOS) {
                FloorTM floorTM = new FloorTM(
                        floorDTO.getFloorId(),
                        floorDTO.getDescription()
                );
                floorTMS.add(floorTM);
            }

            tblFloor.setItems(floorTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void refresh() {
        loadNextFloorId();
        loadTable();
        txtDesc.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColFloorId.setCellValueFactory(new PropertyValueFactory<>("floorId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadNextFloorId();
        loadTable();
    }
}
