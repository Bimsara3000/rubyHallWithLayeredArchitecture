package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationRoomDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationRoomBO extends SuperBO {
    public String getRoomIds(String reservationId) throws SQLException, ClassNotFoundException;
    public String getResDate(String reservationId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllPossibleRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException;
    public boolean saveReservationRoom(ReservationRoomDTO reservationRoomDTO, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean deleteReservationRoom(String resId, Connection connection) throws SQLException, ClassNotFoundException;
}
