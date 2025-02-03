package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.PaymentDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl {
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from payment");

        ArrayList<PaymentDTO> payments = new ArrayList<>();

        while (resultSet.next()) {
            PaymentDTO payment = new PaymentDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            payments.add(payment);
        }
        return payments;
    }
}
