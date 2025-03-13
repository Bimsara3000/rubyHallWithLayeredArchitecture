package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentTypeDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPaymentViewController implements Initializable {
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    GuestBO guestBO = (GuestBO) BOFactory.getInstance().getBO(BOFactory.BOType.GUEST);
    QueryBO queryBO = (QueryBO) BOFactory.getInstance().getBO(BOFactory.BOType.QUERY);
    ReservationBO reservationBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION);
    PaymentTypeBO paymentTypeBO = (PaymentTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT_TYPE);

    @FXML
    private ComboBox<String> cmbGuestName;

    @FXML
    private ComboBox<String> cmbPaymentType;

    @FXML
    private DatePicker dPPayments;

    @FXML
    private Label lblPaymentAmount;

    @FXML
    private Label lblPaymentId;

    String isDate = "\\d{4}-\\d{2}-\\d{2}";

    @FXML
    void OnGuestSelection(ActionEvent event) {
        cmbGuestName.setStyle(cmbGuestName.getStyle() + ";-fx-border-color: #7367F0;");
        loadAmount();
    }

    @FXML
    void OnPaymentTypeSelection(ActionEvent event) {
        cmbPaymentType.setStyle(cmbPaymentType.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @FXML
    void OnDateSelection(ActionEvent event) {
        dPPayments.setStyle(dPPayments.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @FXML
    void onClickAdd(ActionEvent event) throws SQLException {
        String guestName = cmbGuestName.getValue();
        String paymentType = cmbPaymentType.getValue();
        String date = "";

        if (dPPayments.getValue() != null ){
            date = dPPayments.getValue().toString();
        }

        cmbGuestName.setStyle(cmbGuestName.getStyle() + ";-fx-border-color: #7367F0;");
        cmbPaymentType.setStyle(cmbPaymentType.getStyle() + ";-fx-border-color: #7367F0;");
        dPPayments.setStyle(dPPayments.getStyle() + ";-fx-border-color: #7367F0;");

        if (guestName == null) {
            cmbGuestName.setStyle(cmbGuestName.getStyle() + ";-fx-border-color: red;");
        }

        if (paymentType == null) {
            cmbPaymentType.setStyle(cmbPaymentType.getStyle() + ";-fx-border-color: red;");
        }

        if (date.equals("") || !date.matches(isDate)) {
            dPPayments.setStyle(dPPayments.getStyle() + ";-fx-border-color: red;");
        }

        if (guestName != null && paymentType != null && !date.equals("") && date.matches(isDate)) {
            try {
                boolean isSaved = paymentBO.save(new PaymentDTO(
                        lblPaymentId.getText(),
                        getReservationId(),
                        paymentTypeBO.getPaymentTypeId(paymentType),
                        date,
                        Double.parseDouble(lblPaymentAmount.getText())
                ));

                if (isSaved) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Payment saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save payment!").show();
                }
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    @FXML
    void onClickClear(ActionEvent event) {
        refreshPage();
    }

    private void loadNextPaymentId() {
        try {
            String nextPaymentId = paymentBO.getNextId();
            lblPaymentId.setText(nextPaymentId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadGuests() throws SQLException {
        try {
            ArrayList<GuestDTO> guestDTOS = guestBO.getAll();
            ArrayList<String> guests = new ArrayList<>();

            for (GuestDTO guestDTO : guestDTOS) {
                guests.add(guestDTO.getName());
            }

            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(guests);
            cmbGuestName.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadPaymentTypes() throws SQLException {
        try {
            ArrayList<PaymentTypeDTO> paymentTypeDTOS = paymentTypeBO.getAll();
            ArrayList<String> paymentTypes = new ArrayList<>();

            for (PaymentTypeDTO paymentTypeDTO : paymentTypeDTOS) {
                paymentTypes.add(paymentTypeDTO.getDescription());
            }

            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(paymentTypes);
            cmbPaymentType.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadAmount() {
        try {
            String amount = queryBO.getPaymentAmount(getReservationId());
            lblPaymentAmount.setText(amount);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private String getReservationId() throws SQLException {
        String guestName = cmbGuestName.getValue();

        try {
            String guestId = guestBO.getGuestId(guestName);
            return reservationBO.getReservationId(guestId);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
        return null;
    }

    private void refreshPage() {
        loadNextPaymentId();

        try {
            loadGuests();
            loadPaymentTypes();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        }

        cmbGuestName.getSelectionModel().clearSelection();
        cmbPaymentType.getSelectionModel().clearSelection();
        dPPayments.getEditor().clear();

        cmbGuestName.setStyle(cmbGuestName.getStyle() + ";-fx-border-color: #7367F0;");
        cmbPaymentType.setStyle(cmbPaymentType.getStyle() + ";-fx-border-color: #7367F0;");
        dPPayments.setStyle(dPPayments.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNextPaymentId();
        try {
            loadGuests();
            loadPaymentTypes();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
    }
}
