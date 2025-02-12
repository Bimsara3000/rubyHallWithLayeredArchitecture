package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.ReservationTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ReservationTM, String> tColDesc;

    @FXML
    private TableColumn<ReservationTM, String> tColResDate;

    @FXML
    private TableColumn<ReservationTM, Integer> tColGuestCount;

    @FXML
    private TableColumn<ReservationTM, String> tColGuestName;

    @FXML
    private TableColumn<ReservationTM, String> tColPackageName;

    @FXML
    private TableColumn<ReservationTM, String> tColResId;

    @FXML
    private TableColumn<ReservationTM, String> tColRoomIds;

    @FXML
    private TableColumn<ReservationTM, String> tColDate;

    @FXML
    private TableColumn<ReservationTM, String> tColServices;

    @FXML
    private TableView<ReservationTM> tblReservation;

    @FXML
    void ClickDelete(ActionEvent event) {

    }

    @FXML
    void clickAddReservation(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/AddReservationView.fxml", "Add Reservation", "icons8-add-48.png");
    }

    @FXML
    void clickUpdate(ActionEvent event) {

    }

    @FXML
    void onClickRow(MouseEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    ReservationBO reservationBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION);
    GuestBO guestBO = (GuestBO) BOFactory.getInstance().getBO(BOFactory.BOType.GUEST);
    PackageBO packageBO = (PackageBO) BOFactory.getInstance().getBO(BOFactory.BOType.PACKAGE);
    ReservationRoomBO reservationRoomBO = (ReservationRoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_ROOM);
    ServiceBO serviceBO = (ServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);
    ReservationServiceBO reservationServiceBO = (ReservationServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_SERVICE);

    public void loadTable() {
        try {
            ArrayList<ReservationDTO> reservationDTOS = reservationBO.getAll();

            ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

            for (ReservationDTO reservationDTO : reservationDTOS) {
                ReservationTM reservationTM = new ReservationTM(
                        reservationDTO.getReservationId(),
                        guestBO.getName(reservationDTO.getGuestId()),
                        packageBO.getName(reservationDTO.getPackageId()),
                        serviceBO.getServices(reservationServiceBO.getServiceId(reservationDTO.getReservationId())),
                        reservationRoomBO.getRoomIds(reservationDTO.getReservationId()),
                        reservationDTO.getGuestCount(),
                        reservationDTO.getDate(),
                        reservationRoomBO.getResDate(reservationDTO.getReservationId()),
                        reservationDTO.getDescription()
                );
                reservationTMS.add(reservationTM);
            }

            tblReservation.setItems(reservationTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColResId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        tColServices.setCellValueFactory(new PropertyValueFactory<>("services"));
        tColGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        tColPackageName.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        tColRoomIds.setCellValueFactory(new PropertyValueFactory<>("roomIds"));
        tColGuestCount.setCellValueFactory(new PropertyValueFactory<>("guestCount"));
        tColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tColResDate.setCellValueFactory(new PropertyValueFactory<>("resDate"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        loadTable();
    }

    public void navigateTo(String fxmlPath, String title, String imageName) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle(title);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/"+imageName)));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
