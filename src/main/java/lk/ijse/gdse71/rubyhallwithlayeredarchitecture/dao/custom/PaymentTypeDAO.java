package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.PaymentType;

import java.sql.SQLException;

public interface PaymentTypeDAO extends CrudDAO<PaymentType> {
    public String getPaymentDescription(String paymentTypeId) throws SQLException, ClassNotFoundException;
    public String getPaymentTypeId(String paymentType) throws SQLException, ClassNotFoundException;
}
