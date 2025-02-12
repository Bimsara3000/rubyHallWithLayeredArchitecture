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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FacilityBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FacilityDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.FacilityTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class FacilityViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblFacilityId;

    @FXML
    private TableColumn<FacilityTM, String> tColDesc;

    @FXML
    private TableColumn<FacilityTM, String> tColFacilityId;

    @FXML
    private TableColumn<FacilityTM, Double> tColPrice;

    @FXML
    private TableView<FacilityTM> tblFacility;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtPrice;

    FacilityBO facilityBO = (FacilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.FACILITY);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        FacilityTM facilityTM = tblFacility.getSelectionModel().getSelectedItem();

        if (facilityTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Facility!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this facility?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = facilityBO.delete(facilityTM.getFacilityId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The facility is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the facility!").show();
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
        String price = txtPrice.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String pricePattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidPrice = price.matches(pricePattern);

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && !desc.isEmpty()) {
            try {
                FacilityDTO facilityDTO  = new FacilityDTO(
                        lblFacilityId.getText(),
                        desc,
                        Double.parseDouble(price)
                );

                boolean isSaved = facilityBO.save(facilityDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Facility saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save facility!").show();
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
        FacilityTM facilityTM = tblFacility.getSelectionModel().getSelectedItem();

        if (facilityTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Facility!").show();
            return;
        }

        String desc = txtDesc.getText();
        String price = txtPrice.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String pricePattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidPrice = price.matches(pricePattern);

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && !desc.isEmpty()) {
            try {
                FacilityDTO facilityDTO  = new FacilityDTO(
                        lblFacilityId.getText(),
                        desc,
                        Double.parseDouble(price)
                );

                boolean isSaved = facilityBO.update(facilityDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Facility updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update facility!").show();
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
        FacilityTM facilityTM = tblFacility.getSelectionModel().getSelectedItem();

        lblFacilityId.setText(facilityTM.getFacilityId());
        txtDesc.setText(facilityTM.getDescription());
        txtPrice.setText(String.valueOf(facilityTM.getPrice()));

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

    public void loadNextFacilityId() {
        try {
            String nextFacilityId = facilityBO.getNextId();
            lblFacilityId.setText(nextFacilityId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<FacilityDTO> facilityDTOS = facilityBO.getAll();

            ObservableList<FacilityTM> facilityTMS = FXCollections.observableArrayList();

            for (FacilityDTO facilityDTO : facilityDTOS) {
                FacilityTM facilityTM = new FacilityTM(
                        facilityDTO.getFacilityId(),
                        facilityDTO.getDescription(),
                        facilityDTO.getPrice()

                );
                facilityTMS.add(facilityTM);
            }

            tblFacility.setItems(facilityTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void refresh() {
        loadNextFacilityId();
        loadTable();
        txtDesc.setText("");
        txtPrice.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColFacilityId.setCellValueFactory(new PropertyValueFactory<>("facilityId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadNextFacilityId();
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
