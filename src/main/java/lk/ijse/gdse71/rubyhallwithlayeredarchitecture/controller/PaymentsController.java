package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.projectrubyhall.dto.PaymentDTO;
import lk.ijse.gdse71.projectrubyhall.dto.tm.PaymentTM;
import lk.ijse.gdse71.projectrubyhall.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentsController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PaymentTM, Double> tColAmount;

    @FXML
    private TableColumn<PaymentTM, String> tColDate;

    @FXML
    private TableColumn<PaymentTM, String> tColPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> tColPaymentType;

    @FXML
    private TableColumn<PaymentTM, String> tColGuestName;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    void onClickAdd(ActionEvent event) {

    }

    @FXML
    void onClickDelete(ActionEvent event) {

    }

    @FXML
    void onClickUpdate(ActionEvent event) {

    }

    @FXML
    void onClickRow(MouseEvent event) {

    }

    @FXML
    void onClickSendEmail(ActionEvent event) {

    }

    PaymentModel paymentModel = new PaymentModel();
    ReservationModel reservationModel = new ReservationModel();
    GuestModel guestModel = new GuestModel();
    PaymentTypeModel paymentTypeModel = new PaymentTypeModel();

    public void loadTable() {
        try {
            ArrayList<PaymentDTO> paymentDTOS = paymentModel.getAllPayments();

            ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO : paymentDTOS) {
                PaymentTM paymentTM = new PaymentTM(
                        paymentDTO.getPaymentId(),
                        guestModel.getGuestName(reservationModel.getGuestId(paymentDTO.getReservationId())),
                        paymentTypeModel.getPaymentDescription(paymentDTO.getPaymentTypeId()),
                        paymentDTO.getDate(),
                        paymentDTO.getAmount()
                );
                paymentTMS.add(paymentTM);
            }

            tblPayment.setItems(paymentTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        tColGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        tColPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        tColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tColAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadTable();
    }
}
