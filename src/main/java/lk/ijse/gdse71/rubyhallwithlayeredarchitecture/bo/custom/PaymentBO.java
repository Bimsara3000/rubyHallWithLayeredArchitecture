package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    public ArrayList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    public boolean update(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException;
}
