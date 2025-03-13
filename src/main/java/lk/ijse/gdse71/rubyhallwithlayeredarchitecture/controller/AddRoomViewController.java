package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.db.DBConnection;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomFacilityDTO;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRoomViewController implements Initializable {

    @FXML
    private CheckBox cBoxBalcony;

    @FXML
    private CheckBox cBoxFireplace;

    @FXML
    private CheckBox cBoxGarden;

    @FXML
    private CheckBox cBoxJacuzzi;

    @FXML
    private CheckBox cBoxSauna;

    @FXML
    private ComboBox<String> cmbFloor;

    @FXML
    private ComboBox<String> cmbRoomType;

    @FXML
    private ComboBox<String> cmbState;

    @FXML
    private Label lblRoomId;

    RoomTypeBO roomTypeBO = (RoomTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_TYPE);
    FloorBO floorBO = (FloorBO) BOFactory.getInstance().getBO(BOFactory.BOType.FLOOR);
    RoomBO roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM);
    FacilityBO facilityBO = (FacilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.FACILITY);
    RoomFacilityBO roomFacilityBO = (RoomFacilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_FACILITY);

    Connection connection = DBConnection.getInstance().getConnection();

    public AddRoomViewController() throws SQLException {
    }

    @FXML
    void onClickAdd(ActionEvent event) throws SQLException {
        String roomType = cmbRoomType.getValue();
        String floor = cmbFloor.getValue();
        String state = cmbState.getValue();

        cmbRoomType.setStyle(cmbRoomType.getStyle() + ";-fx-border-color: #7367F0;");
        cmbFloor.setStyle(cmbFloor.getStyle() + ";-fx-border-color: #7367F0;");
        cmbState.setStyle(cmbState.getStyle() + ";-fx-border-color: #7367F0;");

        if (roomType == null) {
            cmbRoomType.setStyle(cmbRoomType.getStyle() + ";-fx-border-color: red;");
        }

        if (floor == null) {
            cmbFloor.setStyle(cmbFloor.getStyle() + ";-fx-border-color: red;");
        }

        if (state == null) {
            cmbState.setStyle(cmbFloor.getStyle() + ";-fx-border-color: red;");
        }

        try {
            connection.setAutoCommit(false);
            if (roomType != null && floor != null && state != null) {
                RoomDTO roomDTO = new RoomDTO(
                        lblRoomId.getText(),
                        roomTypeBO.getRoomTypeId(roomType),
                        floorBO.getFloorId(floor),
                        state
                );

                boolean isSaved = roomBO.saveRoom(roomDTO,connection);

                if (isSaved) {
                    if (cBoxBalcony.isSelected() || cBoxFireplace.isSelected() || cBoxGarden.isSelected() || cBoxJacuzzi.isSelected() || cBoxSauna.isSelected()) {
                        ArrayList<RoomFacilityDTO> roomFacilities = new ArrayList<>();

                        CheckBox[] checkBoxes = {cBoxBalcony, cBoxFireplace, cBoxGarden, cBoxJacuzzi, cBoxSauna};
                        String[] facility = {"Balcony", "Fireplace", "Garden", "Jacuzzi", "Sauna"};

                        for (int i = 0; i < checkBoxes.length; i++) {
                            if (checkBoxes[i].isSelected()) {
                                RoomFacilityDTO roomFacilityDTO = new RoomFacilityDTO(
                                        lblRoomId.getText(),
                                        facilityBO.getFacilityId(facility[i])
                                );
                                roomFacilities.add(roomFacilityDTO);
                            }
                        }

                        boolean facilitySaved = roomFacilityBO.saveFacilities(roomFacilities,connection);

                        if (facilitySaved) {
                            connection.commit();
                            refreshPage();
                            new Alert(Alert.AlertType.INFORMATION, "Room saved!").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed to save room!").show();
                        }
                    } else {
                        connection.commit();
                        refreshPage();
                        new Alert(Alert.AlertType.INFORMATION, "Room saved!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save room!").show();
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, "Database error!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @FXML
    void onClickClear(ActionEvent event) {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNextRoomId();
        try {
            loadRoomTypes();
            loadFloors();
            loadStates();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        }
    }

    private void loadNextRoomId() {
        try {
            String nextRoomId = roomBO.getNextId();
            lblRoomId.setText(nextRoomId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadStates() {
        cmbState.getItems().addAll("Available", "Occupied");
    }

    private void loadFloors() throws SQLException {
        try {
            ArrayList<String> roomTypes = roomTypeBO.getRoomTypes();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(roomTypes);
            cmbRoomType.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void loadRoomTypes() throws SQLException {
        try {
            ArrayList<String> floors = floorBO.getFloors();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(floors);
            cmbFloor.setItems(observableList);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void refreshPage() {
        loadNextRoomId();

        try {
            loadRoomTypes();
            loadFloors();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        }

        cmbRoomType.getSelectionModel().clearSelection();
        cmbFloor.getSelectionModel().clearSelection();
        cmbState.getSelectionModel().clearSelection();

        cBoxBalcony.setSelected(false);
        cBoxFireplace.setSelected(false);
        cBoxGarden.setSelected(false);
        cBoxJacuzzi.setSelected(false);
        cBoxSauna.setSelected(false);

        cmbRoomType.setStyle(cmbRoomType.getStyle() + ";-fx-border-color: #7367F0;");
        cmbFloor.setStyle(cmbFloor.getStyle() + ";-fx-border-color: #7367F0;");
        cmbState.setStyle(cmbState.getStyle() + ";-fx-border-color: #7367F0;");
    }
}
