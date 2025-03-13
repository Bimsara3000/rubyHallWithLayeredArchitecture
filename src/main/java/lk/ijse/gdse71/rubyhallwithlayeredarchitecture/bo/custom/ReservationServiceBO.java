package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationServiceDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ReservationServiceBO extends SuperBO {
    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException;
    public boolean saveReservationService(ReservationServiceDTO reservationServiceDTO, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean deleteReservationService(String resId, Connection connection) throws SQLException, ClassNotFoundException;
    public String getDuration(String reservationId) throws SQLException, ClassNotFoundException;
}
