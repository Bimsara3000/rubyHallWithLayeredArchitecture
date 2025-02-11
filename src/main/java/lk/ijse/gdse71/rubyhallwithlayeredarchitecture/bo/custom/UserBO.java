package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public UserDTO checkDetails(String uId) throws SQLException, ClassNotFoundException;
    public String getName(String userId) throws SQLException, ClassNotFoundException;
    public ArrayList<UserDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String userId) throws SQLException, ClassNotFoundException;
}
