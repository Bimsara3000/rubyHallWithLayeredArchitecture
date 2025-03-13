package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PaymentBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PaymentDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Payment;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    public ArrayList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();

        for (Payment payment : payments) {
            paymentDTOs.add(new PaymentDTO(payment.getPaymentId(),payment.getReservationId(),payment.getPaymentTypeId(),payment.getDate(),payment.getAmount()));
        }
        return paymentDTOs;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    public boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(paymentDTO.getPaymentId(),paymentDTO.getReservationId(),paymentDTO.getPaymentTypeId(),paymentDTO.getDate(),paymentDTO.getAmount()));
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(paymentDTO.getPaymentId(),paymentDTO.getReservationId(),paymentDTO.getPaymentTypeId(),paymentDTO.getDate(),paymentDTO.getAmount()));
    }

    @Override
    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }
}
