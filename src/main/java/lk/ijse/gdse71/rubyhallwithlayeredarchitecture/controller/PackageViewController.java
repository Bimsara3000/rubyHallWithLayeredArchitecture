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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PackageBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PackageDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.PackageTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PackageViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblPackageId;

    @FXML
    private TableColumn<PackageTM, String> tColDesc;

    @FXML
    private TableColumn<PackageTM, Integer> tColDuration;

    @FXML
    private TableColumn<PackageTM, String> tColName;

    @FXML
    private TableColumn<PackageTM, String> tColPackageId;

    @FXML
    private TableColumn<PackageTM, Double> tColPrice;

    @FXML
    private TableColumn<PackageTM, String> tColValidity;

    @FXML
    private TableView<PackageTM> tblPackage;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtValidity;

    PackageBO packageBO = (PackageBO) BOFactory.getInstance().getBO(BOFactory.BOType.PACKAGE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        PackageTM packageTM = tblPackage.getSelectionModel().getSelectedItem();

        if (packageTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Package!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this package?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = packageBO.delete(packageTM.getPackageId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The package is deleted!").show();
                    refresh();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the package!").show();
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
        String validity = txtValidity.getText();
        String duration = txtDuration.getText();
        String name = txtName.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: #7367F0;");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtValidity.setStyle(txtValidity.getStyle() + ";-fx-border-color: #7367F0;");

        String pricePattern = "^\\d+(\\.\\d{1,2})?$";
        String namePattern = "^[A-Za-z ]+$";
        String isNumeric = "-?\\d+(\\.\\d+)?";

        boolean isValidPrice = price.matches(pricePattern);
        boolean isValidName = name.matches(namePattern);
        boolean isValidDuration = duration.matches(isNumeric);

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidDuration) {
            txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }

        if (validity.isEmpty()) {
            txtValidity.setStyle(txtValidity.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && isValidName && isValidDuration && !validity.isEmpty() && !desc.isEmpty()) {
            try {
                PackageDTO packageDTO = new PackageDTO(
                        lblPackageId.getText(),
                        name,
                        desc,
                        Integer.parseInt(duration),
                        Double.parseDouble(price),
                        validity
                );

                boolean isSaved = packageBO.save(packageDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Package saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save package!").show();
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
        PackageTM packageTM = tblPackage.getSelectionModel().getSelectedItem();

        if (packageTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Package!").show();
            return;
        }

        String desc = txtDesc.getText();
        String price = txtPrice.getText();
        String validity = txtValidity.getText();
        String duration = txtDuration.getText();
        String name = txtName.getText();

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: #7367F0;");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtValidity.setStyle(txtValidity.getStyle() + ";-fx-border-color: #7367F0;");

        String pricePattern = "^\\d+(\\.\\d{1,2})?$";
        String namePattern = "^[A-Za-z ]+$";
        String isNumeric = "-?\\d+(\\.\\d+)?";

        boolean isValidPrice = price.matches(pricePattern);
        boolean isValidName = name.matches(namePattern);
        boolean isValidDuration = duration.matches(isNumeric);

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidDuration) {
            txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }

        if (validity.isEmpty()) {
            txtValidity.setStyle(txtValidity.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && isValidName && isValidDuration && !validity.isEmpty() && !desc.isEmpty()) {
            try {
                PackageDTO packageDTO = new PackageDTO(
                        lblPackageId.getText(),
                        name,
                        desc,
                        Integer.parseInt(duration),
                        Double.parseDouble(price),
                        validity
                );

                boolean isSaved = packageBO.update(packageDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Package updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update package!").show();
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
        PackageTM packageTM = tblPackage.getSelectionModel().getSelectedItem();

        lblPackageId.setText(packageTM.getPackageId());
        txtDesc.setText(packageTM.getDescription());
        txtPrice.setText(String.valueOf(packageTM.getPrice()));
        txtValidity.setText(String.valueOf(packageTM.getValidity()));
        txtName.setText(packageTM.getName());
        txtDuration.setText(String.valueOf(packageTM.getDuration()));

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: #7367F0;");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtValidity.setStyle(txtValidity.getStyle() + ";-fx-border-color: #7367F0;");
        refresh();
    }

    public void loadNextPackageId() {
        try {
            String nextPackageId = packageBO.getNextId();
            lblPackageId.setText(nextPackageId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<PackageDTO> packageDTOS = packageBO.getAll();

            ObservableList<PackageTM> packageTMS = FXCollections.observableArrayList();

            for (PackageDTO packageDTO : packageDTOS) {
                PackageTM packageTM = new PackageTM(
                        packageDTO.getPackageId(),
                        packageDTO.getName(),
                        packageDTO.getDescription(),
                        packageDTO.getDuration(),
                        packageDTO.getPrice(),
                        packageDTO.getValidity()
                );
                packageTMS.add(packageTM);
            }

            tblPackage.setItems(packageTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void refresh() {
        loadNextPackageId();
        loadTable();
        txtDesc.setText("");
        txtPrice.setText("");
        txtValidity.setText("");
        txtName.setText("");
        txtDuration.setText("");
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        tColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tColDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tColValidity.setCellValueFactory(new PropertyValueFactory<>("validity"));
        loadNextPackageId();
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
