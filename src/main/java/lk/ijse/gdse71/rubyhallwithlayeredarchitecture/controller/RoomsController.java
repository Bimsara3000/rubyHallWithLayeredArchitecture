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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FloorBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomFacilityBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomTypeBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.db.DBConnection;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.RoomTM;

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

    RoomTypeBO roomTypeBO = (RoomTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_TYPE);
    FloorBO floorBO = (FloorBO) BOFactory.getInstance().getBO(BOFactory.BOType.FLOOR);
    RoomBO roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM);
    RoomFacilityBO roomFacilityBO = (RoomFacilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_FACILITY);

    Connection connection = DBConnection.getInstance().getConnection();

    public RoomsController() throws SQLException {
    }

    @FXML
    void OnClickAdd(ActionEvent event) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/AddRoomView.fxml"));

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
                boolean isDeleted = roomBO.deleteRoom(room.getRoomId(),connection);
                if (isDeleted) {
                    if (!room.getFacilities().isEmpty()) {
                        boolean isFacilitiesDeleted = roomFacilityBO.deleteFacilities(room.getRoomId(),connection);

                        if (!isFacilitiesDeleted) {
                            connection.commit();
                            new Alert(Alert.AlertType.INFORMATION, "The room is deleted!").show();
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
    void OnClickUpdate(ActionEvent event) {
        RoomTM room = tblRoom.getSelectionModel().getSelectedItem();

        if (room == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a room!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.gdse71.rubyhallwithlayeredarchitecture/UpdateRoomView.fxml"));
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

    public void loadTable() {
        try {
            ArrayList<RoomDTO> roomDTOS = roomBO.getAll();

            ObservableList<RoomTM> roomTMS = FXCollections.observableArrayList();

            for (RoomDTO roomDTO : roomDTOS) {
                RoomTM roomTM = new RoomTM(
                        roomDTO.getRoomId(),
                        roomTypeBO.getName(roomDTO.getRoomTypeId()),
                        floorBO.getName(roomDTO.getFloorId()),
                        roomDTO.getState(),
                        roomFacilityBO.getFacilities(roomDTO.getRoomId())
                );
                roomTMS.add(roomTM);
            }

            tblRoom.setItems(roomTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
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
