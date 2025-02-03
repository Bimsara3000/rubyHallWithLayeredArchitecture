package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.projectrubyhall.dto.PaymentTypeDTO;
import lk.ijse.gdse71.projectrubyhall.dto.tm.PaymentTypeTM;
import lk.ijse.gdse71.projectrubyhall.model.PaymentTypeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentTypeViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblPaymentTypeId;

    @FXML
    private TableColumn<PaymentTypeTM, String> tColDesc;

    @FXML
    private TableColumn<PaymentTypeTM, String> tColPaymentTypeId;

    @FXML
    private TableView<PaymentTypeTM> tblPaymentType;

    @FXML
    private TextField txtDesc;

    PaymentTypeModel paymentTypeModel = new PaymentTypeModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        PaymentTypeTM paymentTypeTM = tblPaymentType.getSelectionModel().getSelectedItem();

        if (paymentTypeTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment type?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = paymentTypeModel.deletePaymentType(paymentTypeTM.getPaymentTypeId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The payment type is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the payment type!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
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
                PaymentTypeDTO paymentTypeDTO  = new PaymentTypeDTO(
                        lblPaymentTypeId.getText(),
                        desc
                );

                boolean isSaved = paymentTypeModel.savePaymentType(paymentTypeDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Payment type saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Payment type!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        PaymentTypeTM paymentTypeTM = tblPaymentType.getSelectionModel().getSelectedItem();

        if (paymentTypeTM == null) {
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
                PaymentTypeDTO paymentTypeDTO  = new PaymentTypeDTO(
                        lblPaymentTypeId.getText(),
                        desc
                );

                boolean isSaved = paymentTypeModel.updatePaymentType(paymentTypeDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Payment type updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Payment type!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        PaymentTypeTM paymentTypeTM = tblPaymentType.getSelectionModel().getSelectedItem();

        lblPaymentTypeId.setText(paymentTypeTM.getPaymentTypeId());
        txtDesc.setText(paymentTypeTM.getDescription());

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

    public void loadNextPaymentTypeId() {
        try {
            String nextPaymentTypeId = paymentTypeModel.getNextPaymentTypeId();
            lblPaymentTypeId.setText(nextPaymentTypeId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
    }

    public void loadTable() {
        try {
            ArrayList<PaymentTypeDTO> paymentTypeDTOS = paymentTypeModel.getAllPaymentTypes();

            ObservableList<PaymentTypeTM> paymentTypeTMS = FXCollections.observableArrayList();

            for (PaymentTypeDTO paymentTypeDTO : paymentTypeDTOS) {
                PaymentTypeTM paymentTypeTM = new PaymentTypeTM(
                        paymentTypeDTO.getPaymentTypeId(),
                        paymentTypeDTO.getDescription()
                );
                paymentTypeTMS.add(paymentTypeTM);
            }

            tblPaymentType.setItems(paymentTypeTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        }
    }

    public void refresh() {
        loadNextPaymentTypeId();
        loadTable();
        txtDesc.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColPaymentTypeId.setCellValueFactory(new PropertyValueFactory<>("paymentTypeId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadNextPaymentTypeId();
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
