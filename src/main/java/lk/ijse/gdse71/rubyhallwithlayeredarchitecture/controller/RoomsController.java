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
import lk.ijse.gdse71.projectrubyhall.db.DBConnection;
import lk.ijse.gdse71.projectrubyhall.dto.RoomDTO;
import lk.ijse.gdse71.projectrubyhall.dto.tm.RoomTM;
import lk.ijse.gdse71.projectrubyhall.model.FloorModel;
import lk.ijse.gdse71.projectrubyhall.model.RoomFacilityModel;
import lk.ijse.gdse71.projectrubyhall.model.RoomModel;
import lk.ijse.gdse71.projectrubyhall.model.RoomTypeModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<RoomTM, String> tColFacilities;

    @FXML
    private TableColumn<RoomTM, String> tColFloor;

    @FXML
    private TableColumn<RoomTM, String> tColRoomId;

    @FXML
    private TableColumn<RoomTM, String> tColRoomType;

    @FXML
    private TableColumn<RoomTM, String> tColState;

    @FXML
    private TableView<RoomTM> tblRoom;

    Connection connection = DBConnection.getInstance().getConnection();

    public RoomsController() throws SQLException {
    }

    @FXML
    void OnClickAdd(ActionEvent event) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AddRoomView.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Room");
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
    void OnClickDelete(ActionEvent event) throws SQLException {
        RoomTM room = tblRoom.getSelectionModel().getSelectedItem();

        if (room == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a room!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this room?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                connection.setAutoCommit(false);
                boolean isDeleted = roomModel.deleteRoom(room.getRoomId());
                if (isDeleted) {
                    if (!room.getFacilities().isEmpty()) {
                        boolean isFacilitiesDeleted = roomFacilityModel.deleteFacilities(room.getRoomId());

                        if (isFacilitiesDeleted) {
                            connection.commit();
                            new Alert(Alert.AlertType.INFORMATION, "The room is deleted!").show();
                            System.out.println("isFacilitiesDeleted: "+isFacilitiesDeleted);
                            loadTable();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Fail to delete this room!").show();
                            System.out.println("Facilities failed to delete!");
                            System.out.println("isFacilitiesDeleted: "+isFacilitiesDeleted);
                            loadTable();
                        }
                    } else {
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION, "The room is deleted!").show();
                        loadTable();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the room!").show();
                    System.out.println("Room655 failed to delete!");
                }
            } catch (SQLException e) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    @FXML
    void OnClickUpdate(ActionEvent event) {
        RoomTM room = tblRoom.getSelectionModel().getSelectedItem();

        if (room == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a room!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateRoomView.fxml"));
            Parent load = loader.load();

            UpdateRoomViewController updateRoomViewController = loader.getController();
            updateRoomViewController.setRoom(room);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Update Room");
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

    RoomModel roomModel = new RoomModel();
    RoomFacilityModel roomFacilityModel = new RoomFacilityModel();
    RoomTypeModel roomTypeModel = new RoomTypeModel();
    FloorModel floorModel = new FloorModel();

    public void loadTable() {
        try {
            ArrayList<RoomDTO> roomDTOS = roomModel.getAllRooms();

            ObservableList<RoomTM> roomTMS = FXCollections.observableArrayList();

            for (RoomDTO roomDTO : roomDTOS) {
                RoomTM roomTM = new RoomTM(
                        roomDTO.getRoomId(),
                        roomTypeModel.getRoomType(roomDTO.getRoomTypeId()),
                        floorModel.getFloor(roomDTO.getFloorId()),
                        roomDTO.getState(),
                        roomFacilityModel.getFacilities(roomDTO.getRoomId())
                );
                roomTMS.add(roomTM);
            }

            tblRoom.setItems(roomTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tColRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        tColFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        tColState.setCellValueFactory(new PropertyValueFactory<>("state"));
        tColFacilities.setCellValueFactory(new PropertyValueFactory<>("facilities"));
        loadTable();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
}
