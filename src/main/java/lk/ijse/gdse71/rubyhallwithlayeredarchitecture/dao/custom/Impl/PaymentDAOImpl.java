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
        ResultSet rst = CrudUtil.execute("select paymentId from payment order by paymentId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into payment values (?,?,?,?,?)",
                payment.getPaymentId(),
                payment.getReservationId(),
                payment.getPaymentTypeId(),
                payment.getDate(),
                payment.getAmount()
        );
    }

    @Override
    public boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update payment set reservationId=?,paymentTypeId=?,date=?,amount=? where paymentId=?",
                payment.getReservationId(),
                payment.getPaymentTypeId(),
                payment.getDate(),
                payment.getAmount(),
                payment.getPaymentId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from payment where paymentId = ?",
                id
        );
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }
}
