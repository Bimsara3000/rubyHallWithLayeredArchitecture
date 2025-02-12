package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomType;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomTypeDAO extends CrudDAO<RoomType> {
    public ArrayList<String> getRoomTypes() throws SQLException, ClassNotFoundException;
    public String getRoomTypeId(String roomType) throws SQLException, ClassNotFoundException;
}
