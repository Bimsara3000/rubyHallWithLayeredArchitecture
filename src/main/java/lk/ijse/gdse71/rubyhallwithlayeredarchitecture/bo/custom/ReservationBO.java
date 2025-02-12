package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    public ArrayList<ReservationDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public String getGuestId(String reservationId) throws SQLException, ClassNotFoundException;
}
