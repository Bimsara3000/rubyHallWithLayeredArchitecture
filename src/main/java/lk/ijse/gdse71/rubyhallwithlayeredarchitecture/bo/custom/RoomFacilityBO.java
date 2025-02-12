package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomFacilityDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomFacility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomFacilityBO extends SuperBO {
    public String getFacilities(String roomId) throws SQLException, ClassNotFoundException;
    public boolean saveFacilities(ArrayList<RoomFacilityDTO> roomFacilities, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean delete(String roomId) throws SQLException, ClassNotFoundException;
    public boolean deleteFacilities(String roomId, Connection connection) throws SQLException, ClassNotFoundException;
}
