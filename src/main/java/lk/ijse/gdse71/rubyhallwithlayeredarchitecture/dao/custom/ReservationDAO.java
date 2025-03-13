package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Reservation;

import java.sql.Connection;
import java.sql.SQLException;

public interface ReservationDAO extends CrudDAO<Reservation> {
    public String getGuestId(String reservationId) throws SQLException, ClassNotFoundException;
    public String getReservationId(String guestId) throws SQLException, ClassNotFoundException;
    public boolean saveReservation(Reservation reservation, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean updateReservation(Reservation reservation, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean deleteReservation(String reservationId, Connection connection) throws SQLException, ClassNotFoundException;
}
