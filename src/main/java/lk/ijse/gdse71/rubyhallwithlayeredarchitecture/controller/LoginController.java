package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.gdse71.projectrubyhall.dto.JobRoleDTO;
import lk.ijse.gdse71.projectrubyhall.dto.UserDTO;
import lk.ijse.gdse71.projectrubyhall.model.JobRoleModel;
import lk.ijse.gdse71.projectrubyhall.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane anchLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserID;

    @FXML
    private Button btnLog;

    @FXML
    private Label lblRRS;

    @FXML
    private ImageView picLogo;

    @FXML
    private VBox vBoxLogin;

    UserModel userModel = new UserModel();
    JobRoleModel jobRoleModel = new JobRoleModel();
    public static String jobRole;
    public static String userId;

    @FXML
    void btnLogin(ActionEvent event) {

        try {
            UserDTO details = userModel.checkDetails(txtUserID.getText());

            if(details != null && details .getPassword().equals(txtPassword.getText())) {

                JobRoleDTO role = jobRoleModel.getJobRole(details.getJobRoleId());

                if(role != null) {
                    jobRole = role.getName();
                    userId = details.getUserId();
                }

                anchLogin.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));

                anchLogin.getChildren().add(load);
            } else {
                new Alert(Alert.AlertType.ERROR, "User ID or/and Password is/are incorrect!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "DB Error!").show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Not Found!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchLogin.setTopAnchor(vBoxLogin, 0.0);
        anchLogin.setBottomAnchor(vBoxLogin, 0.0);
        anchLogin.setLeftAnchor(vBoxLogin, 0.0);
        anchLogin.setRightAnchor(vBoxLogin, 0.0);
    }
}
