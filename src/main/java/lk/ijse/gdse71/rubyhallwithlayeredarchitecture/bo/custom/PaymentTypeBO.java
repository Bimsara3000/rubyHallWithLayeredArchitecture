package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentTypeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentTypeBO extends SuperBO {
    public String getPaymentDescription(String paymentTypeId) throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentTypeDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean save(PaymentTypeDTO paymentTypeDTO) throws SQLException, ClassNotFoundException;
    public boolean update(PaymentTypeDTO paymentTypeDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String paymentTypeId) throws SQLException, ClassNotFoundException;
    public String getPaymentTypeId(String paymentType) throws SQLException, ClassNotFoundException;;
}
