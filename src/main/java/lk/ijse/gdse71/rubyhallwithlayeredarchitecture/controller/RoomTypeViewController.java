package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomTypeBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomTypeDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.RoomTypeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomTypeViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblRoomTypeId;

    @FXML
    private TableColumn<RoomTypeTM, String> tColDesc;

    @FXML
    private TableColumn<RoomTypeTM, Double> tColPrice;

    @FXML
    private TableColumn<RoomTypeTM, String> tColRoomTypeId;

    @FXML
    private TableView<RoomTypeTM> tblRoomType;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtPrice;

    RoomTypeBO roomTypeBO = (RoomTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.ROOM_TYPE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @FXML
    void resetOnAction(ActionEvent event) {

    }

    public void loadNextRoomTypeId() {
        try {
            String nextRoomTypeId = roomTypeBO.getNextId();
            lblRoomTypeId.setText(nextRoomTypeId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<RoomTypeDTO> roomTypeDTOS = roomTypeBO.getAll();

            ObservableList<RoomTypeTM> roomTypeTMS = FXCollections.observableArrayList();

            for (RoomTypeDTO roomTypeDTO : roomTypeDTOS) {
                RoomTypeTM roomTypeTM = new RoomTypeTM(
                        roomTypeDTO.getRoomTypeId(),
                        roomTypeDTO.getDescription(),
                        roomTypeDTO.getPrice()
                );
                roomTypeTMS.add(roomTypeTM);
            }

            tblRoomType.setItems(roomTypeTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadNextRoomTypeId();
        loadTable();
    }
}
