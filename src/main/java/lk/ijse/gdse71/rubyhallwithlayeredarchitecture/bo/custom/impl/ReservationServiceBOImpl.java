package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationServiceBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationServiceDAO;

import java.sql.SQLException;

public class ReservationServiceBOImpl implements ReservationServiceBO {
    ReservationServiceDAO reservationServiceDAO = (ReservationServiceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVATION_SERVICE);

    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException {
        return reservationServiceDAO.getServiceId(reservationId);
    }
}
