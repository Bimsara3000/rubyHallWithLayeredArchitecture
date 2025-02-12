package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Reservation;

import java.sql.SQLException;

public interface ReservationDAO extends CrudDAO<Reservation> {
    public String getGuestId(String reservationId) throws SQLException, ClassNotFoundException;
}
