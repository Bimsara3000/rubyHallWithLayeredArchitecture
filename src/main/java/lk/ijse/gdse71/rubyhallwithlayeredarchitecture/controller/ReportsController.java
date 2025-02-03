package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.projectrubyhall.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportsController {

    @FXML
    private TextField txtGuestName;

    @FXML
    void onClickGenerateGuestReport(ActionEvent event) {

        if (txtGuestName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Guest Name").show();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/GuestReservationDetailsReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Guest_Name", txtGuestName.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    void onClickGenerateAllGuestReport(ActionEvent event) {

    }

    @FXML
    void onClickGenerateAllPaymentReport(ActionEvent event) {

    }

    @FXML
    void onClickGeneratePaymentReport(ActionEvent event) {

        if (txtGuestName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Guest Name").show();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/GuestReservationDetailsReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Guest_Name", txtGuestName.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

}
