package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomDAO extends CrudDAO<Room> {
    public ArrayList<String> getRooms(String rId, String fId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllAvailableRooms() throws SQLException, ClassNotFoundException;
}
