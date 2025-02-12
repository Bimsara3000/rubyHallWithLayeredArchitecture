package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;

import java.sql.SQLException;

public interface ReservationServiceBO extends SuperBO {
    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException;
}
