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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FacilityDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomTypeDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.FacilityTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.FloorTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.RoomTypeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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
        RoomTypeTM roomTypeTM = tblRoomType.getSelectionModel().getSelectedItem();

        if (roomTypeTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a room type!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this room type?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = roomTypeBO.delete(roomTypeTM.getRoomTypeId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The room type is deleted!").show();
                    refresh();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the room type!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String desc = txtDesc.getText();
        String price = txtPrice.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String pricePattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidPrice = price.matches(pricePattern);

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && !desc.isEmpty()) {
            try {
                RoomTypeDTO roomTypeDTO = new RoomTypeDTO(
                        lblRoomTypeId.getText(),
                        desc,
                        Double.parseDouble(price)
                );

                boolean isSaved = roomTypeBO.save(roomTypeDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Room type saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save room type!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String desc = txtDesc.getText();
        String price = txtPrice.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String pricePattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidPrice = price.matches(pricePattern);

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && !desc.isEmpty()) {
            try {
                RoomTypeDTO roomTypeDTO = new RoomTypeDTO(
                        lblRoomTypeId.getText(),
                        desc,
                        Double.parseDouble(price)
                );

                boolean isSaved = roomTypeBO.update(roomTypeDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Room type updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update room type!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Class not found!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        RoomTypeTM roomTypeTM = tblRoomType.getSelectionModel().getSelectedItem();

        lblRoomTypeId.setText(roomTypeTM.getRoomTypeId());
        txtDesc.setText(roomTypeTM.getDescription());
        txtPrice.setText(String.valueOf(roomTypeTM.getPrice()));

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) { refresh(); }

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

    public void refresh() {
        loadNextRoomTypeId();
        loadTable();
        txtDesc.setText("");
        txtPrice.setText("");
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadNextRoomTypeId();
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
