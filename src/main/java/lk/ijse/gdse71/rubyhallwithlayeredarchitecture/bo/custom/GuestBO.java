package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuestBO extends SuperBO {
    public ArrayList<GuestDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(GuestDTO guestDTO) throws SQLException, ClassNotFoundException;
    public boolean update(GuestDTO guestDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public String getName(String id) throws SQLException, ClassNotFoundException;
}
