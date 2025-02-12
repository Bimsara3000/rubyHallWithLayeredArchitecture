package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PaymentTypeBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PaymentTypeDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PaymentTypeDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.PaymentType;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentTypeBOImpl implements PaymentTypeBO {
    PaymentTypeDAO paymentTypeDAO = (PaymentTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT_TYPE);

    public String getPaymentDescription(String paymentTypeId) throws SQLException, ClassNotFoundException {
        return paymentTypeDAO.getPaymentDescription(paymentTypeId);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return paymentTypeDAO.getNextId();
    }

    public ArrayList<PaymentTypeDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentType> paymentTypes = paymentTypeDAO.getAll();
        ArrayList<PaymentTypeDTO> paymentTypeDTOs = new ArrayList<>();

        for (PaymentType paymentType : paymentTypes) {
            PaymentTypeDTO paymentTypeDTO = new PaymentTypeDTO(
                    paymentType.getPaymentTypeId(),
                    paymentType.getDescription()
            );
            paymentTypeDTOs.add(paymentTypeDTO);
        }
        return paymentTypeDTOs;
    }

    public boolean save(PaymentTypeDTO paymentTypeDTO) throws SQLException, ClassNotFoundException {
        return paymentTypeDAO.save(new PaymentType(paymentTypeDTO.getPaymentTypeId(),paymentTypeDTO.getDescription()));
    }

    public boolean update(PaymentTypeDTO paymentTypeDTO) throws SQLException, ClassNotFoundException {
        return paymentTypeDAO.update(new PaymentType(paymentTypeDTO.getPaymentTypeId(),paymentTypeDTO.getDescription()));
    }

    public boolean delete(String paymentTypeId) throws SQLException, ClassNotFoundException {
        return paymentTypeDAO.delete(paymentTypeId);
    }
}
