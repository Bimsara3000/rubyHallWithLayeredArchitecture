package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.SuperDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public String getPaymentAmount(String reservationId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllAvailableRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException;
    public ArrayList<String> searchAllAvailableRooms(String startDate, String endDate, String roomTypeId, String floorId) throws SQLException, ClassNotFoundException;
}
