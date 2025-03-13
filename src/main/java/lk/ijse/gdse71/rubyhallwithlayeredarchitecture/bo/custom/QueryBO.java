package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryBO extends SuperBO {
    String getPaymentAmount(String reservationId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllAvailableRooms(String startDate,String endDate) throws SQLException, ClassNotFoundException;
    public ArrayList<String> searchAllAvailableRooms(String startDate, String endDate, String roomTypeId, String floorId) throws SQLException, ClassNotFoundException;
}
