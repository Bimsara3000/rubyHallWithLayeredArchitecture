package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    public ArrayList<RoomDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(RoomDTO roomDTO) throws SQLException, ClassNotFoundException;
    public boolean update(RoomDTO roomDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String roomId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getRooms(String rId, String fId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllAvailableRooms() throws SQLException, ClassNotFoundException;
    public boolean deleteRoom(String roomId, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean updateRoom(RoomDTO room, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean saveRoom(RoomDTO roomDTO, Connection connection) throws SQLException, ClassNotFoundException;
}
