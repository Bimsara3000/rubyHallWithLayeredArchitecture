package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.projectrubyhall.dto.UserDTO;
import lk.ijse.gdse71.projectrubyhall.dto.tm.UserTM;
import lk.ijse.gdse71.projectrubyhall.model.JobRoleModel;
import lk.ijse.gdse71.projectrubyhall.model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbJobRole;

    @FXML
    private Label lblUserId;

    @FXML
    private TableColumn<UserTM, String> tColEmail;

    @FXML
    private TableColumn<UserTM, String> tColJobRole;

    @FXML
    private TableColumn<UserTM, String> tColName;

    @FXML
    private TableColumn<UserTM, String> tColPassword;

    @FXML
    private TableColumn<UserTM, String> tColUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    UserModel userModel = new UserModel();
    JobRoleModel jobRoleModel = new JobRoleModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        UserTM user = tblUser.getSelectionModel().getSelectedItem();

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a user!").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            try {
                boolean isDeleted = userModel.deleteUser(user.getUserId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "The user is deleted!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the user!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "DB Error!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (password.isEmpty()) {
            txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidEmail && !password.isEmpty()) {
            try {
                UserDTO userDTO = new UserDTO(
                        lblUserId.getText(),
                        jobRoleModel.getJobRoleId(cmbJobRole.getValue()),
                        name,
                        email,
                        password
                );

                boolean isSaved = userModel.saveUser(userDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "User saved!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        UserTM user = tblUser.getSelectionModel().getSelectedItem();

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a user!").show();
            return;
        }

        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (password.isEmpty()) {
            txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidEmail && !password.isEmpty()) {
            try {
                UserDTO userDTO = new UserDTO(
                        lblUserId.getText(),
                        jobRoleModel.getJobRoleId(cmbJobRole.getValue()),
                        name,
                        email,
                        password
                );

                boolean isSaved = userModel.updateUser(userDTO);

                if (isSaved) {
                    refresh();
                    new Alert(Alert.AlertType.INFORMATION, "User updated!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update user!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM user = tblUser.getSelectionModel().getSelectedItem();

        lblUserId.setText(user.getUserId());
        txtEmail.setText(user.getEmail());
        txtName.setText(user.getName());
        txtPassword.setText(user.getPassword());
        cmbJobRole.setValue(user.getJobRole());

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

    public void loadTable() {
        try {
            ArrayList<UserDTO> userDTOS = userModel.getAllUsers();

            ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

            for (UserDTO userDTO : userDTOS) {
                UserTM userTM = new UserTM(
                        userDTO.getUserId(),
                        jobRoleModel.getJobRoleName(userDTO.getJobRoleId()),
                        userDTO.getName(),
                        userDTO.getEmail(),
                        userDTO.getPassword()
                );
                userTMS.add(userTM);
            }

            tblUser.setItems(userTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Can't load data to the table!").show();
        }
    }

    public void loadNextServiceId() {
        try {
            String nextUserId = userModel.getNextUserId();
            lblUserId.setText(nextUserId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
    }

    private void loadJobRoles() throws SQLException {
        ArrayList<String> jobRoles = jobRoleModel.getJobRoles();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(jobRoles);
        cmbJobRole.setItems(observableList);
    }

    public void refresh() {
        loadNextServiceId();
        loadTable();
        try {
            loadJobRoles();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
        txtEmail.setText("");
        txtName.setText("");
        txtPassword.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tColUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tColJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        tColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        loadNextServiceId();
        loadTable();
        try {
            loadJobRoles();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Can't connect to Database!").show();
            e.printStackTrace();
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
