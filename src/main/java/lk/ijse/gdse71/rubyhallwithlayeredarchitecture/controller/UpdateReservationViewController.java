package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.db.DBConnection;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.ReservationTM;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateReservationViewController implements Initializable {

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnChooseRoom;

    @FXML
    private Button btnClear;

    @FXML
    private ComboBox<String> cmbGuest;

    @FXML
    private ComboBox<String> cmbPackage;

    @FXML
    private ComboBox<String> cmbService;

    @FXML
    private DatePicker dPEDate;

    @FXML
    private DatePicker dPSDate;

    @FXML
    private Label lblResId;

    @FXML
    private Label lblRoomId;

    @FXML
    private Label lblUserName;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtGuestCount;

    @FXML
    private TextField txtServiceDuration;

    public static String roomIds;

    @Setter
    ReservationTM reservationTM;

    ReservationBO reservationBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION);
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    GuestBO guestBO = (GuestBO) BOFactory.getInstance().getBO(BOFactory.BOType.GUEST);
    PackageBO packageBO = (PackageBO) BOFactory.getInstance().getBO(BOFactory.BOType.PACKAGE);
    ServiceBO serviceBO = (ServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);
    ReservationRoomBO reservationRoomBO = (ReservationRoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_ROOM);
    ReservationServiceBO reservationServiceBO = (ReservationServiceBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_SERVICE);

    Connection connection = DBConnection.getInstance().getConnection();

    public UpdateReservationViewController() throws SQLException {
    }

    @FXML
    void onActionCmbGuest(ActionEvent event) {
        cmbGuest.setStyle(cmbGuest.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @FXML
    void onActionCmbPackage(ActionEvent event) {
        cmbPackage.setStyle(cmbPackage.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @FXML
    void onActionCmbService(ActionEvent event) {
        cmbService.setStyle(cmbService.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @FXML
    void onClickUpdate(ActionEvent event) throws SQLException {
        String resId = lblResId.getText();
        String userId = LoginController.userId;
        String startDate = "";
        String endDate = "";
        String rooms = lblRoomId.getText();
        String guest = cmbGuest.getValue();
        String packages = cmbPackage.getValue();
        String service = cmbService.getValue();
        String serviceDuration = txtServiceDuration.getText();
        String guestCount = txtGuestCount.getText();
        String desc = txtDesc.getText();

        String regex = "[0-9]+";

        boolean gCount = guestCount.matches(regex);

        if (dPSDate.getValue() != null ){
            startDate = dPSDate.getValue().toString();
        }

        if (dPEDate.getValue() != null ){
            endDate = dPEDate.getValue().toString();
        }

        cmbGuest.setStyle(cmbGuest.getStyle() + ";-fx-border-color: #7367F0;");
        cmbPackage.setStyle(cmbPackage.getStyle() + ";-fx-border-color: #7367F0;");
        cmbService.setStyle(cmbService.getStyle() + ";-fx-border-color: #7367F0;");
        dPSDate.setStyle(dPSDate.getStyle() + ";-fx-border-color: #7367F0;");
        dPEDate.setStyle(dPEDate.getStyle() + ";-fx-border-color: #7367F0;");
        lblRoomId.setStyle(lblRoomId.getStyle() + ";-fx-border-color: #7367F0;");
        txtGuestCount.setStyle(txtGuestCount.getStyle() + ";-fx-border-color: #7367F0;");
        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtServiceDuration.setStyle(txtServiceDuration.getStyle() + ";-fx-border-color: #7367F0;");

        if (rooms.isEmpty()) {
            lblRoomId.setStyle(lblRoomId.getStyle() + ";-fx-border-color: red;");
        }

        if (guest == null) {
            cmbGuest.setStyle(cmbGuest.getStyle() + ";-fx-border-color: red;");
        }

        if (packages == null) {
            cmbPackage.setStyle(cmbPackage.getStyle() + ";-fx-border-color: red;");
        }

        if (service == null) {
            cmbService.setStyle(cmbService.getStyle() + ";-fx-border-color: red;");
        }

        if (serviceDuration.isEmpty()) {
            txtServiceDuration.setStyle(txtServiceDuration.getStyle() + ";-fx-border-color: red;");
        }

        if (guestCount.isEmpty() || !gCount) {
            txtGuestCount.setStyle(txtGuestCount.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!startDate.equals("") && !endDate.equals("") && !rooms.isEmpty() && guest != null && packages != null && service != null && !serviceDuration.isEmpty() && !guestCount.isEmpty() && gCount && !desc.isEmpty()) {
            try {
                connection.setAutoCommit(false);

                ReservationDTO reservationDTO = new ReservationDTO(
                        resId,
                        userId,
                        guestBO.getGuestId(guest),
                        packageBO.getPackageId(packages),
                        Integer.parseInt(guestCount),
                        String.valueOf(LocalDate.now()),
                        desc
                );

                boolean reservationSaved = reservationBO.updateReservation(reservationDTO,connection);

                if (reservationSaved) {

                    boolean reservationRoomDeleted = reservationRoomBO.deleteReservationRoom(resId,connection);

                    if (reservationRoomDeleted) {
                        String[] roomArray = rooms.split(",");

                        boolean reservationRoomSaved = true;

                        for (int i = 0; i < roomArray.length; i++) {
                            boolean isSaved = reservationRoomBO.saveReservationRoom(new ReservationRoomDTO(
                                            resId,
                                            roomArray[i],
                                            startDate,
                                            endDate),
                                    connection
                            );

                            if (!isSaved) {
                                reservationRoomSaved = false;
                            }
                        }

                        if (reservationRoomSaved) {

                            boolean reservationServiceDeleted = reservationServiceBO.deleteReservationService(resId,connection);

                            if (reservationServiceDeleted) {

                                boolean reservationServiceSaved = reservationServiceBO.saveReservationService(new ReservationServiceDTO(
                                                serviceBO.getServiceId(service),
                                                resId,
                                                Integer.parseInt(serviceDuration)),
                                        connection
                                );

                                if (reservationServiceSaved) {
                                    connection.commit();
                                    refreshPage();
                                    new Alert(Alert.AlertType.INFORMATION, "Reservation updated!").show();
                                    Stage stage = (Stage) btnUpdate.getScene().getWindow();
                                    stage.close();
                                } else {
                                    connection.rollback();
                                    new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
                                }

                            } else {
                                connection.rollback();
                                new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
                            }
                        } else {
                            connection.rollback();
                            new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
                        }

                    } else {
                        connection.rollback();
                        new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
                    }
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Failed to update reservation!").show();
                }

            } catch (SQLException e) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    @FXML
    void OnEDateSelection(ActionEvent event) {
        dPEDate.setStyle(dPEDate.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @FXML
    void OnSDateSelection(ActionEvent event) {
        dPSDate.setStyle(dPSDate.getStyle() + ";-fx-border-color: #7367F0;");
    }

    String isDate = "\\d{4}-\\d{2}-\\d{2}";

    @FXML
    void onClickChooseRoom(ActionEvent event) {
        String startDate = "";
        String endDate = "";

        if (dPSDate.getValue() != null ){
            startDate = dPSDate.getValue().toString();
        }

        if (dPEDate.getValue() != null ){
            endDate = dPEDate.getValue().toString();
        }

        dPSDate.setStyle(dPSDate.getStyle() + ";-fx-border-color: #7367F0;");
        dPEDate.setStyle(dPEDate.getStyle() + ";-fx-border-color: #7367F0;");

        if (startDate.equals("") || !startDate.matches(isDate)) {
            dPSDate.setStyle(dPSDate.getStyle() + ";-fx-border-color: red;");
        }

        if (endDate.equals("") || !endDate.matches(isDate)) {
            dPEDate.setStyle(dPEDate.getStyle() + ";-fx-border-color: red;");
        }

        if (!startDate.equals("") && startDate.matches(isDate) && !endDate.equals("") && endDate.matches(isDate)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/ChooseRoom.fxml"));
                Parent load = loader.load();

                ChooseRoomController chooseRoomController = loader.getController();

                chooseRoomController.setStartDate(startDate);
                chooseRoomController.setEndDate(endDate);

                if (lblRoomId != null) {
                    chooseRoomController.setRooms(lblRoomId.getText());
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.setTitle("Choose Room");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons8-room-48.png")));

                stage.initModality(Modality.APPLICATION_MODAL);

                Window underWindow = btnChooseRoom.getScene().getWindow();
                stage.initOwner(underWindow);

                stage.showAndWait();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
                e.printStackTrace();
            }
            lblRoomId.setText(roomIds);
        }
    }

    @FXML
    void onClickClear(ActionEvent event) {
        refreshPage();
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
            cmbGuest.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadPackages() throws SQLException {
        try {
            ArrayList<PackageDTO> packageDTOS = packageBO.getAll();
            ArrayList<String> packages = new ArrayList<>();

            for (PackageDTO packageDTO : packageDTOS) {
                packages.add(packageDTO.getName());
            }

            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(packages);
            cmbPackage.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadServices() throws SQLException {
        try {
            ArrayList<ServiceDTO> serviceDTOS = serviceBO.getAll();
            ArrayList<String> services = new ArrayList<>();

            for (ServiceDTO serviceDTO : serviceDTOS) {
                services.add(serviceDTO.getDescription());
            }

            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(services);
            cmbService.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void refreshPage() {
        try {
            loadGuests();
            loadPackages();
            loadServices();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        }

        cmbGuest.getSelectionModel().clearSelection();
        cmbPackage.getSelectionModel().clearSelection();
        cmbService.getSelectionModel().clearSelection();
        dPSDate.getEditor().clear();
        dPEDate.getEditor().clear();
        lblRoomId.setText("");
        txtGuestCount.setText("");
        txtDesc.setText("");
        txtServiceDuration.setText("");

        cmbGuest.setStyle(cmbGuest.getStyle() + ";-fx-border-color: #7367F0;");
        cmbPackage.setStyle(cmbPackage.getStyle() + ";-fx-border-color: #7367F0;");
        cmbService.setStyle(cmbService.getStyle() + ";-fx-border-color: #7367F0;");
        dPSDate.setStyle(dPSDate.getStyle() + ";-fx-border-color: #7367F0;");
        dPEDate.setStyle(dPEDate.getStyle() + ";-fx-border-color: #7367F0;");
        lblRoomId.setStyle(lblRoomId.getStyle() + ";-fx-border-color: #7367F0;");
        txtGuestCount.setStyle(txtGuestCount.getStyle() + ";-fx-border-color: #7367F0;");
        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtServiceDuration.setStyle(txtServiceDuration.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if (reservationTM == null) {
                System.out.println("Null Reservation TM!");
                return;
            }

            lblResId.setText(reservationTM.getReservationId());
            lblRoomId.setText(reservationTM.getRoomIds());
            roomIds = reservationTM.getRoomIds();
            cmbGuest.setValue(reservationTM.getGuestName());
            cmbPackage.setValue(reservationTM.getPackageName());
            cmbService.setValue(reservationTM.getServices());

            try {
                String date = reservationTM.getResDate();
                String[] dates = date.split(" : ");

                dPSDate.setValue(LocalDate.parse(dates[0]));
                dPEDate.setValue(LocalDate.parse(dates[1]));
                txtServiceDuration.setText(reservationServiceBO.getDuration(reservationTM.getReservationId()));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }

            txtGuestCount.setText(Integer.toString(reservationTM.getGuestCount()));
            txtDesc.setText(reservationTM.getDescription());
        });

        try {
            loadGuests();
            loadPackages();
            loadServices();
            lblUserName.setText(userBO.getName(LoginController.userId));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }
}
