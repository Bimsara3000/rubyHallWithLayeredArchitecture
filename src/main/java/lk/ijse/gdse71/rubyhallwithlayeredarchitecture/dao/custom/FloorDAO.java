package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Floor;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FloorDAO extends CrudDAO<Floor> {
    public ArrayList<String> getFloors() throws SQLException, ClassNotFoundException;
    public String getFloorId(String floor) throws SQLException, ClassNotFoundException;
}
