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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.db.DBConnection;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.ReservationTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.RoomTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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

    ReservationBO reservationBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION);
    GuestBO guestBO = (GuestBO) BOFactory.getInstance().getBO(BOFactory.BOType.GUEST);
    PackageBO packageBO = (PackageBO) BOFactory.getInstance().getBO(BOFactory.BOType.PACKAGE);
    ReservationRoomBO reservationRoomBO = (ReservationRoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_ROOM);
    ServiceBO serviceBO = (ServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);
    ReservationServiceBO reservationServiceBO = (ReservationServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_SERVICE);

    Connection connection = DBConnection.getInstance().getConnection();

    public ReservationsController() throws SQLException {
    }

    @FXML
    void ClickDelete(ActionEvent event) throws SQLException {
        ReservationTM reservationTM = tblReservation.getSelectionModel().getSelectedItem();

        if (reservationTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a reservation!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                connection.setAutoCommit(false);
                boolean isReservationDeleted = reservationBO.deleteReservation(reservationTM.getReservationId(),connection);

                if (isReservationDeleted) {

                    boolean isReservationRoomDeleted = reservationRoomBO.deleteReservationRoom(reservationTM.getReservationId(),connection);

                    if (!isReservationRoomDeleted) {

                        boolean isReservationServiceDeleted = reservationServiceBO.deleteReservationService(reservationTM.getReservationId(),connection);

                        if (!isReservationServiceDeleted) {
                            connection.commit();
                            new Alert(Alert.AlertType.INFORMATION, "The reservation is deleted!").show();
                            loadTable();
                        } else {
                            connection.rollback();
                            new Alert(Alert.AlertType.ERROR, "Failed to delete the reservation!").show();
                            System.out.println("service");
                        }

                    } else {
                        connection.rollback();
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the reservation!").show();
                        System.out.println("room");
                    }
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the reservation!").show();
                    System.out.println("reservation");
                }
            } catch (SQLException e) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    @FXML
    void clickAddReservation(ActionEvent event) {
        navigateTo("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/AddReservationView.fxml", "Add Reservation", "icons8-add-48.png");
        loadTable();
    }

    @FXML
    void clickUpdate(ActionEvent event) {
        ReservationTM reservationTM = tblReservation.getSelectionModel().getSelectedItem();

        if (reservationTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a reservation!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/UpdateReservationView.fxml"));
            Parent load = loader.load();

            UpdateReservationViewController updateReservationViewController = loader.getController();
            updateReservationViewController.setReservationTM(reservationTM);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Update Reservation");
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
    void onClickRow(MouseEvent event) {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

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
