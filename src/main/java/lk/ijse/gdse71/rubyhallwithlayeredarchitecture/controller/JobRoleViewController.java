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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.JobRoleBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FloorDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.JobRoleDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.FloorTM;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.JobRoleTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class JobRoleViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblJobRoleId;

    @FXML
    private TableColumn<JobRoleTM, String> tColJobRoleId;

    @FXML
    private TableColumn<JobRoleTM, String> tColName;

    @FXML
    private TableView<JobRoleTM> tblJobRole;

    @FXML
    private TextField txtDesc;

    JobRoleBO jobRoleBO = (JobRoleBO) BOFactory.getInstance().getBO(BOFactory.BOType.JOB_ROLE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        JobRoleTM jobRoleTM = tblJobRole.getSelectionModel().getSelectedItem();

        if (jobRoleTM == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a job role!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this job role?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = jobRoleBO.delete(jobRoleTM.getJobRoleId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The job role is deleted!").show();
                    refresh();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the job role!").show();
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

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!desc.isEmpty()) {
            try {
                JobRoleDTO jobRoleDTO = new JobRoleDTO(
                        lblJobRoleId.getText(),
                        desc
                );

                boolean isSaved = jobRoleBO.save(jobRoleDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Job Role saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Job Role!").show();
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

        txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: #7367F0;");

        if (desc.isEmpty()) {
            txtDesc.setStyle(txtDesc.getStyle() + ";-fx-border-color: red;");
        }

        if (!desc.isEmpty()) {
            try {
                JobRoleDTO jobRoleDTO = new JobRoleDTO(
                        lblJobRoleId.getText(),
                        desc
                );

                boolean isSaved = jobRoleBO.update(jobRoleDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Job Role updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Job Role!").show();
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
        JobRoleTM jobRoleTM = tblJobRole.getSelectionModel().getSelectedItem();

        lblJobRoleId.setText(jobRoleTM.getJobRoleId());
        txtDesc.setText(jobRoleTM.getName());

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) { refresh(); }

    public void loadNextJobRoleId() {
        try {
            String nextJobRoleId = jobRoleBO.getNextId();
            lblJobRoleId.setText(nextJobRoleId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void loadTable() {
        try {
            ArrayList<JobRoleDTO> jobRoleDTOS = jobRoleBO.getAll();

            ObservableList<JobRoleTM> jobRoleTMS = FXCollections.observableArrayList();

            for (JobRoleDTO jobRoleDTO: jobRoleDTOS) {
                JobRoleTM jobRoleTM = new JobRoleTM(
                        jobRoleDTO.getJobRoleId(),
                        jobRoleDTO.getName()
                );
                jobRoleTMS.add(jobRoleTM);
            }

            tblJobRole.setItems(jobRoleTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Class not found!").show();
        }
    }

    public void refresh() {
        loadNextJobRoleId();
        loadTable();
        txtDesc.setText("");
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColJobRoleId.setCellValueFactory(new PropertyValueFactory<>("jobRoleId"));
        tColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadNextJobRoleId();
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
