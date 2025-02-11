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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PriceFlucBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PriceFlucDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.PriceFlucTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PriceFlucViewController implements Initializable {
    PriceFlucBO priceFlucBO = (PriceFlucBO) BOFactory.getInstance(BOFactory.BOType.PRICE_FLUC);

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblPriceFlucId;

    @FXML
    private TableColumn<PriceFlucTM, String> tColDesc;

    @FXML
    private TableColumn<PriceFlucTM, String> tColEDate;

    @FXML
    private TableColumn<PriceFlucTM, Integer> tColPercentage;

    @FXML
    private TableColumn<PriceFlucTM, String> tColPriceFlucId;

    @FXML
    private TableColumn<PriceFlucTM, String> tColSDate;

    @FXML
    private TableView<PriceFlucTM> tblPriceFluc;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtEDate;

    @FXML
    private TextField txtPercentage;

    @FXML
    private TextField txtSDate;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        PriceFlucTM priceFlucTM = tblPriceFluc.getSelectionModel().getSelectedItem();

        if (priceFlucTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Price Fluctuation!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this price fluctuation?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = priceFlucBO.delete(priceFlucTM.getPriceFlucId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The price fluctuation is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the price fluctuation!").show();
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
        String sDate = txtSDate.getText();
        String eDate = txtEDate.getText();
        String percentage = txtPercentage.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtSDate.setStyle(txtSDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtEDate.setStyle(txtEDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtPercentage.setStyle(txtPercentage.getStyle() + ";-fx-border-color: #7367F0;");

        String isInteger = "-?\\d+";
        String isDate = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        boolean isValidEDate = eDate.matches(isDate);
        boolean isValidSDate = sDate.matches(isDate);
        boolean isValidPercentage = percentage.matches(isInteger);

        if (!isValidEDate) {
            txtEDate.setStyle(txtEDate.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidSDate) {
            txtSDate.setStyle(txtSDate.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPercentage) {
            txtPercentage.setStyle(txtPercentage.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidSDate && isValidEDate && isValidPercentage && !desc.isEmpty()) {
            try {
                PriceFlucDTO priceFlucDTO  = new PriceFlucDTO(
                        lblPriceFlucId.getText(),
                        desc,
                        sDate,
                        eDate,
                        Integer.parseInt(percentage)
                );

                boolean isSaved = priceFlucBO.save(priceFlucDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Price Fluctuation saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Price Fluctuation!").show();
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
        PriceFlucTM priceFlucTM = tblPriceFluc.getSelectionModel().getSelectedItem();

        if (priceFlucTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Price Fluctuation!").show();
            return;
        }

        String desc = txtDesc.getText();
        String sDate = txtSDate.getText();
        String eDate = txtEDate.getText();
        String percentage = txtPercentage.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtSDate.setStyle(txtSDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtEDate.setStyle(txtEDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtPercentage.setStyle(txtPercentage.getStyle() + ";-fx-border-color: #7367F0;");

        String isInteger = "-?\\d+";
        String isDate = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        boolean isValidEDate = eDate.matches(isDate);
        boolean isValidSDate = sDate.matches(isDate);
        boolean isValidPercentage = percentage.matches(isInteger);

        if (!isValidEDate) {
            txtEDate.setStyle(txtEDate.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidSDate) {
            txtSDate.setStyle(txtSDate.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPercentage) {
            txtPercentage.setStyle(txtPercentage.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidSDate && isValidEDate && isValidPercentage && !desc.isEmpty()) {
            try {
                PriceFlucDTO priceFlucDTO  = new PriceFlucDTO(
                        lblPriceFlucId.getText(),
                        desc,
                        sDate,
                        eDate,
                        Integer.parseInt(percentage)
                );

                boolean isSaved = priceFlucBO.update(priceFlucDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Price Fluctuation updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Price Fluctuation!").show();
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
        PriceFlucTM priceFlucTM = tblPriceFluc.getSelectionModel().getSelectedItem();

        lblPriceFlucId.setText(priceFlucTM.getPriceFlucId());
        txtDesc.setText(priceFlucTM.getDescription());
        txtSDate.setText(priceFlucTM.getSDate());
        txtEDate.setText(priceFlucTM.getEDate());
        txtPercentage.setText(String.valueOf(priceFlucTM.getPercentage()));

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

    public void loadNextPriceFlucId() {
        try {
            String nextPriceFlucId = priceFlucBO.getNextId();
            lblPriceFlucId.setText(nextPriceFlucId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<PriceFlucDTO> priceFlucDTOS = priceFlucBO.getAll();

            ObservableList<PriceFlucTM> priceFlucTMS = FXCollections.observableArrayList();

            for (PriceFlucDTO priceFlucDTO : priceFlucDTOS) {
                PriceFlucTM priceFlucTM = new PriceFlucTM(
                        priceFlucDTO.getPriceFlucId(),
                        priceFlucDTO.getDescription(),
                        priceFlucDTO.getSDate(),
                        priceFlucDTO.getEDate(),
                        priceFlucDTO.getPercentage()
                );
                priceFlucTMS.add(priceFlucTM);
            }

            tblPriceFluc.setItems(priceFlucTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void refresh() {
        loadNextPriceFlucId();
        loadTable();
        txtDesc.setText("");
        txtEDate.setText("");
        txtPercentage.setText("");
        txtSDate.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColPriceFlucId.setCellValueFactory(new PropertyValueFactory<>("priceFlucId"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tColSDate.setCellValueFactory(new PropertyValueFactory<>("sDate"));
        tColEDate.setCellValueFactory(new PropertyValueFactory<>("eDate"));
        tColPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        loadNextPriceFlucId();
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
