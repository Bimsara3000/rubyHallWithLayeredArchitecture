package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FloorBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationRoomBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomTypeBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.ChooseRoomTM;
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

    RoomTypeBO roomTypeBO = (RoomTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_TYPE);
    FloorBO floorBO = (FloorBO) BOFactory.getInstance().getBO(BOFactory.BOType.FLOOR);
    RoomBO roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM);
    ReservationRoomBO reservationRoomBO = (ReservationRoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_ROOM);

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
            ArrayList<String> rooms = roomBO.getRooms(roomTypeBO.getRoomTypeId(roomType.getText()), floorBO.getFloorId(floor.getText()));

            ObservableList<ChooseRoomTM> chooseRoomTMS = FXCollections.observableArrayList();



        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<String> rooms = roomBO.getAllAvailableRooms();

            ObservableList<ChooseRoomTM> chooseRoomTMS = FXCollections.observableArrayList();

            for (String room : rooms) {
                ChooseRoomTM chooseRoomTM = new ChooseRoomTM(room);
                chooseRoomTMS.add(chooseRoomTM);
            }

            ArrayList<String> rooms1 = reservationRoomBO.getAllPossibleRooms(startDate, endDate);

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
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            loadTable();
        });

    }
}
