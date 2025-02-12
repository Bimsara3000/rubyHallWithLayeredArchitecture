package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationRoom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationRoomDAO extends CrudDAO<ReservationRoom> {
    public String getRoomIds(String reservationId) throws SQLException, ClassNotFoundException;
    public String getResDate(String reservationId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllPossibleRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException;
}
