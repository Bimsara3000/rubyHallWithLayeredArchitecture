package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    public ArrayList<ReservationDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public String getGuestId(String reservationId) throws SQLException, ClassNotFoundException;
    public String getReservationId(String guestId) throws SQLException, ClassNotFoundException;
    public boolean saveReservation(ReservationDTO reservationDTO, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean updateReservation(ReservationDTO reservationDTO, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean deleteReservation(String reservationId, Connection connection) throws SQLException, ClassNotFoundException;
}
