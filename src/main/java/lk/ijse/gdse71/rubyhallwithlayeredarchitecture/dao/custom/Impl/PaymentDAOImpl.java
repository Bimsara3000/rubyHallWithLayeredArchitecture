package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PaymentDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from payment");

        ArrayList<Payment> payments = new ArrayList<>();

        while (resultSet.next()) {
            Payment payment = new Payment(
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

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean save(Payment dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }
}
