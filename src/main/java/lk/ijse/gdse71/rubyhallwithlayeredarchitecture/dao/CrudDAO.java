package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(T dto) throws SQLException, ClassNotFoundException;
    public boolean update(T dto) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public String getName(String id) throws SQLException, ClassNotFoundException;
}
