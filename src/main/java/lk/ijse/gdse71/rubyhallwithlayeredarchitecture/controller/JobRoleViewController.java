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
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.JobRoleDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm.JobRoleTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColJobRoleId.setCellValueFactory(new PropertyValueFactory<>("jobRoleId"));
        tColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadNextJobRoleId();
        loadTable();
    }
}
