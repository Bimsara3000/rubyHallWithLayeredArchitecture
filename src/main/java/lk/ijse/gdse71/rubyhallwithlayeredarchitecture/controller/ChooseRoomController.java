package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.gdse71.projectrubyhall.dto.tm.ChooseRoomTM;
import lk.ijse.gdse71.projectrubyhall.model.FloorModel;
import lk.ijse.gdse71.projectrubyhall.model.ReservationRoomModel;
import lk.ijse.gdse71.projectrubyhall.model.RoomModel;
import lk.ijse.gdse71.projectrubyhall.model.RoomTypeModel;
import lombok.Setter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseRoomController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDone;

    @FXML
    private Button btnSearch;

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
    private Label lblRooms;

    @FXML
    private RadioButton rBtn1Floor;

    @FXML
    private RadioButton rBtn2Floor;

    @FXML
    private RadioButton rBtnBanquetHall;

    @FXML
    private RadioButton rBtnGroundFloor;

    @FXML
    private RadioButton rBtnLuxary;

    @FXML
    private RadioButton rBtnStandard;

    @FXML
    private TableColumn<ChooseRoomTM, String> tColRoomId;

    @FXML
    private ToggleGroup tGrpFloor;

    @FXML
    private ToggleGroup tGrpRoomType;

    @FXML
    private TableView<ChooseRoomTM> tblSelectRoom;

    @Setter
    private String startDate;

    @Setter
    private String endDate;

    RoomModel roomModel = new RoomModel();
    FloorModel floorModel = new FloorModel();
    RoomTypeModel roomTypeModel = new RoomTypeModel();
    ReservationRoomModel reservationRoomModel = new ReservationRoomModel();

    @FXML
    void onClickAdd(ActionEvent event) {

    }

    @FXML
    void onClickClear(ActionEvent event) {

    }

    @FXML
    void onClickDone(ActionEvent event) {

    }

    @FXML
    void onClickSearch(ActionEvent event) {
        RadioButton roomType = (RadioButton) tGrpRoomType.getSelectedToggle();
        RadioButton floor = (RadioButton) tGrpFloor.getSelectedToggle();

        try {
            ArrayList<String> rooms = roomModel.getRooms(roomTypeModel.getRoomTypeId(roomType.getText()), floorModel.getFloorId(floor.getText()));

            ObservableList<ChooseRoomTM> chooseRoomTMS = FXCollections.observableArrayList();



        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        }
    }

    public void loadTable() {
        try {
            ArrayList<String> rooms = roomModel.getAllAvailableRooms();

            ObservableList<ChooseRoomTM> chooseRoomTMS = FXCollections.observableArrayList();

            for (String room : rooms) {
                ChooseRoomTM chooseRoomTM = new ChooseRoomTM(room);
                chooseRoomTMS.add(chooseRoomTM);
            }

            ArrayList<String> rooms1 = reservationRoomModel.getAllPossibleRooms(startDate, endDate);

            if (rooms1 != null) {
                for (String room : rooms1) {
                    ChooseRoomTM chooseRoomTM = new ChooseRoomTM(room);
                    chooseRoomTMS.add(chooseRoomTM);
                }
            }

            tblSelectRoom.setItems(chooseRoomTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            loadTable();
        });

    }
}
