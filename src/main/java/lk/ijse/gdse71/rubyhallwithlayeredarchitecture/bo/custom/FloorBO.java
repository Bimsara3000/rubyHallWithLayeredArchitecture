package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FloorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FloorBO extends SuperBO {
    public String getName(String floorId) throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<FloorDTO> getAll() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getFloors() throws SQLException, ClassNotFoundException;
    public String getFloorId(String floor) throws SQLException, ClassNotFoundException;
    public boolean save(FloorDTO floorDTO) throws SQLException, ClassNotFoundException;
    public boolean update(FloorDTO floorDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String floorId) throws SQLException, ClassNotFoundException;
}
