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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PaymentBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PaymentTypeBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.GuestTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.PaymentTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.RoomTM;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class PaymentsController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSendEmail;

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
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/AddPaymentView.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Payment");
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
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();

        if (paymentTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = paymentBO.delete(paymentTM.getPaymentId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The payment is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the payment!").show();
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
    void onClickUpdate(ActionEvent event) {
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();

        if (paymentTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/UpdatePaymentView.fxml"));
            Parent load = loader.load();

            UpdatePaymentViewController updatePaymentViewController = loader.getController();
            updatePaymentViewController.setPaymentTM(paymentTM);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Update Payment");
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

    @FXML
    void OnClickRow(MouseEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnSendEmail.setDisable(false);
    }

    @FXML
    void onClickSendEmail(ActionEvent event) {
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();

        if (paymentTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/SendMailView.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();
            sendMailController.setPaymentTM(paymentTM);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Email");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons8-mail-64.png")));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load UI!");
            e.printStackTrace();
        }
    }

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    ReservationBO reservationBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION);
    GuestBO guestBO = (GuestBO) BOFactory.getInstance().getBO(BOFactory.BOType.GUEST);
    PaymentTypeBO paymentTypeBO = (PaymentTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT_TYPE);

    public void loadTable() {
        try {
            ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAll();

            ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO : paymentDTOS) {
                PaymentTM paymentTM = new PaymentTM(
                        paymentDTO.getPaymentId(),
                        guestBO.getName(reservationBO.getGuestId(paymentDTO.getReservationId())),
                        paymentTypeBO.getPaymentDescription(paymentDTO.getPaymentTypeId()),
                        paymentDTO.getDate(),
                        paymentDTO.getAmount()
                );
                paymentTMS.add(paymentTM);
            }

            tblPayment.setItems(paymentTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
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
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSendEmail.setDisable(true);
    }
}
