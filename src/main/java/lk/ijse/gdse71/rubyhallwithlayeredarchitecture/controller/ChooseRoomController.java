package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.ChooseRoomTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.GuestTM;
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
    private RadioButton rBtnLuxury;

    @FXML
    private RadioButton rBtnStandard;

    @FXML
    private TableColumn<ChooseRoomTM, String> tColRoomId;

    @FXML
    private TableColumn<ChooseRoomTM, String> tColFacilities;

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

    @Setter
    private String rooms;

    RoomTypeBO roomTypeBO = (RoomTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_TYPE);
    FloorBO floorBO = (FloorBO) BOFactory.getInstance().getBO(BOFactory.BOType.FLOOR);
    RoomBO roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM);
    ReservationRoomBO reservationRoomBO = (ReservationRoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESERVATION_ROOM);
    QueryBO queryBO = (QueryBO) BOFactory.getInstance().getBO(BOFactory.BOType.QUERY);
    RoomFacilityBO roomFacilityBO = (RoomFacilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_FACILITY);

    @FXML
    void OnClickRow(MouseEvent event) {

    }

    @FXML
    void onClickAdd(ActionEvent event) {
        ChooseRoomTM chooseRoomTM = tblSelectRoom.getSelectionModel().getSelectedItem();

        if (lblRooms.getText().isEmpty()) {
            lblRooms.setText(chooseRoomTM.getRoomId());
        } else {
            lblRooms.setText(lblRooms.getText() + "," + chooseRoomTM.getRoomId());
        }
    }

    @FXML
    void onClickClear(ActionEvent event) {
        refreshPage();
        lblRooms.setText("");
    }

    @FXML
    void onClickDone(ActionEvent event) {
        AddReservationViewController.roomIds = lblRooms.getText();
        UpdateReservationViewController.roomIds = lblRooms.getText();
        Stage stage = (Stage) btnDone.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickSearch(ActionEvent event) {
        RadioButton roomType = (RadioButton) tGrpRoomType.getSelectedToggle();
        RadioButton floor = (RadioButton) tGrpFloor.getSelectedToggle();

        if (!rBtnGroundFloor.isSelected() && !rBtn1Floor.isSelected() && !rBtn2Floor.isSelected()) {
            rBtnGroundFloor.setStyle(rBtnGroundFloor.getStyle() + ";-fx-border-color: red;");
            rBtn1Floor.setStyle(rBtn1Floor.getStyle() + ";-fx-border-color: red;");
            rBtn2Floor.setStyle(rBtn2Floor.getStyle() + ";-fx-border-color: red;");
        }

        if (!rBtnLuxury.isSelected() && !rBtnStandard.isSelected() && !rBtnBanquetHall.isSelected()) {
            rBtnLuxury.setStyle(rBtnLuxury.getStyle() + ";-fx-border-color: red;");
            rBtnStandard.setStyle(rBtnStandard.getStyle() + ";-fx-border-color: red;");
            rBtnBanquetHall.setStyle(rBtnBanquetHall.getStyle() + ";-fx-border-color: red;");
        }

        if ((rBtnGroundFloor.isSelected() || rBtn1Floor.isSelected() || rBtn2Floor.isSelected()) && (rBtnLuxury.isSelected() || rBtnStandard.isSelected() || rBtnBanquetHall.isSelected())) {
            String roomTypeId;
            String floorId;

            roomTypeId = switch (roomType.getText()) {
                case "Luxury" -> "RT001";
                case "Standard" -> "RT002";
                case "Banquet Hall" -> "RT003";
                default -> "";
            };

            floorId = switch (floor.getText()) {
                case "Ground Floor" -> "F001";
                case "1st Floor" -> "F002";
                case "2nd Floor" -> "F003";
                default -> "";
            };

            rBtnGroundFloor.setStyle(rBtnGroundFloor.getStyle() + ";-fx-border-color: #7367F0;");
            rBtn1Floor.setStyle(rBtn1Floor.getStyle() + ";-fx-border-color: #7367F0;");
            rBtn2Floor.setStyle(rBtn2Floor.getStyle() + ";-fx-border-color: #7367F0;");

            rBtnLuxury.setStyle(rBtnLuxury.getStyle() + ";-fx-border-color: #7367F0;");
            rBtnStandard.setStyle(rBtnStandard.getStyle() + ";-fx-border-color: #7367F0;");
            rBtnBanquetHall.setStyle(rBtnBanquetHall.getStyle() + ";-fx-border-color: #7367F0;");

            try {
                ArrayList<String> rooms = queryBO.searchAllAvailableRooms(startDate,endDate,roomTypeId,floorId);

                ObservableList<ChooseRoomTM> chooseRoomTMS = FXCollections.observableArrayList();

                for (String room : rooms) {
                    chooseRoomTMS.add(new ChooseRoomTM(
                            room,
                            roomFacilityBO.getFacilities(room)
                    ));
                }

                tblSelectRoom.setItems(chooseRoomTMS);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    public void loadTable() {
        try {
            ArrayList<String> rooms = queryBO.getAllAvailableRooms(startDate,endDate);

            ObservableList<ChooseRoomTM> chooseRoomTMS = FXCollections.observableArrayList();

            for (String room : rooms) {
                chooseRoomTMS.add(new ChooseRoomTM(
                        room,
                        roomFacilityBO.getFacilities(room)
                ));
            }

            tblSelectRoom.setItems(chooseRoomTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    private void refreshPage() {
        loadTable();

        rBtnGroundFloor.setSelected(false);
        rBtn1Floor.setSelected(false);
        rBtn2Floor.setSelected(false);

        rBtnLuxury.setSelected(false);
        rBtnStandard.setSelected(false);
        rBtnBanquetHall.setSelected(false);

        rBtnGroundFloor.setStyle(rBtnGroundFloor.getStyle() + ";-fx-border-color: #7367F0;");
        rBtn1Floor.setStyle(rBtn1Floor.getStyle() + ";-fx-border-color: #7367F0;");
        rBtn2Floor.setStyle(rBtn2Floor.getStyle() + ";-fx-border-color: #7367F0;");

        rBtnLuxury.setStyle(rBtnLuxury.getStyle() + ";-fx-border-color: #7367F0;");
        rBtnStandard.setStyle(rBtnStandard.getStyle() + ";-fx-border-color: #7367F0;");
        rBtnBanquetHall.setStyle(rBtnBanquetHall.getStyle() + ";-fx-border-color: #7367F0;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            tColRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
            tColFacilities.setCellValueFactory(new PropertyValueFactory<>("facilities"));
            loadTable();

            if (rooms != null) {
                lblRooms.setText(rooms);
            }
        });
    }
}
