package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PaymentTypeDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.PaymentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentTypeDAOImpl implements PaymentTypeDAO {
    public String getPaymentDescription(String paymentTypeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select description from paymentType where paymentTypeId = ?", paymentTypeId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select paymentTypeId from paymentType order by paymentTypeId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PT%03d", newIdIndex);
        }
        return "PT001";
    }

    public ArrayList<PaymentType> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from paymentType");

        ArrayList<PaymentType> paymentTypes = new ArrayList<>();

        while (resultSet.next()) {
            PaymentType paymentType = new PaymentType(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            paymentTypes.add(paymentType);
        }
        return paymentTypes;
    }

    public boolean save(PaymentType paymentType) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into paymentType values (?,?)",
                paymentType.getPaymentTypeId(),
                paymentType.getDescription()
        );
    }

    public boolean update(PaymentType paymentType) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update paymentType set description=? where paymentTypeDTO=?",
                paymentType.getDescription(),
                paymentType.getPaymentTypeId()
        );
    }

    public boolean delete(String paymentTypeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from paymentType where paymentTypeId = ?",
                paymentTypeId
        );
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }
}
