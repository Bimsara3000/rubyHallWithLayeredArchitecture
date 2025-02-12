package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomFacility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomFacilityDAO extends CrudDAO<RoomFacility> {
    public String getFacilities(String roomId) throws SQLException, ClassNotFoundException;
    public boolean saveFacilities(ArrayList<RoomFacility> roomFacilities, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean deleteFacilities(String roomId, Connection connection) throws SQLException, ClassNotFoundException;
}
