package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomTypeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomTypeBO extends SuperBO {
    public String getName(String roomTypeId) throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<RoomTypeDTO> getAll() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getRoomTypes() throws SQLException, ClassNotFoundException;
    public String getRoomTypeId(String roomType) throws SQLException, ClassNotFoundException;
}
