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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ServiceBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ServiceDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.ServiceTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblServiceId;

    @FXML
    private TableColumn<ServiceTM, String> tColDesc;

    @FXML
    private TableColumn<ServiceTM, Double> tColPrice;

    @FXML
    private TableColumn<ServiceTM, String> tColServiceId;

    @FXML
    private TableView<ServiceTM> tblService;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtPrice;

    ServiceBO serviceBO = (ServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ServiceTM serviceTM = tblService.getSelectionModel().getSelectedItem();

        if (serviceTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Service!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = serviceBO.delete(serviceTM.getServiceId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The service is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the service!").show();
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
                ServiceDTO serviceDTO = new ServiceDTO(
                        lblServiceId.getText(),
                        desc,
                        Double.parseDouble(price)
                );

                boolean isSaved = serviceBO.save(serviceDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Service saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save service!").show();
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
        ServiceTM serviceTM = tblService.getSelectionModel().getSelectedItem();

        if (serviceTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Service!").show();
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
                ServiceDTO serviceDTO = new ServiceDTO(
                        lblServiceId.getText(),
                        desc,
                        Double.parseDouble(price)
                );

                boolean isSaved = serviceBO.update(serviceDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Service updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update service!").show();
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
        ServiceTM serviceTM = tblService.getSelectionModel().getSelectedItem();

        lblServiceId.setText(serviceTM.getServiceId());
        txtDesc.setText(serviceTM.getDescription());
        txtPrice.setText(String.valueOf(serviceTM.getPrice()));

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

    public void loadTable() {
        try {
            ArrayList< ServiceDTO> serviceDTOS = serviceBO.getAll();

            ObservableList<ServiceTM> serviceTMS = FXCollections.observableArrayList();

            for (ServiceDTO serviceDTO : serviceDTOS) {
                ServiceTM serviceTM = new ServiceTM(
                        serviceDTO.getServiceId(),
                        serviceDTO.getDescription(),
                        serviceDTO.getPrice()

                );
                serviceTMS.add(serviceTM);
            }

            tblService.setItems(serviceTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadNextServiceId() {
        try {
            String nextServiceId = serviceBO.getNextId();
            lblServiceId.setText(nextServiceId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void refresh() {
        loadNextServiceId();
        loadTable();
        txtDesc.setText("");
        txtPrice.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadNextServiceId();
        loadTable();
    }
}
