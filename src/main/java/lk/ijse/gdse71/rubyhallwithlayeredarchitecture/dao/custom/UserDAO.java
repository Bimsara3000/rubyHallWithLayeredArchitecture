package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public User checkDetails(String uId) throws SQLException, ClassNotFoundException;
}
