package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationServiceBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationServiceDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationServiceDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationService;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationServiceBOImpl implements ReservationServiceBO {
    ReservationServiceDAO reservationServiceDAO = (ReservationServiceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVATION_SERVICE);

    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException {
        return reservationServiceDAO.getServiceId(reservationId);
    }

    @Override
    public boolean saveReservationService(ReservationServiceDTO reservationServiceDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationServiceDAO.saveReservationService(new ReservationService(
                reservationServiceDTO.getServiceId(),
                reservationServiceDTO.getReservationId(),
                reservationServiceDTO.getDuration()),
                connection
        );
    }

    @Override
    public boolean deleteReservationService(String resId, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationServiceDAO.deleteReservationService(resId,connection);
    }

    @Override
    public String getDuration(String reservationId) throws SQLException, ClassNotFoundException {
        return reservationServiceDAO.getDuration(reservationId);
    }
}
