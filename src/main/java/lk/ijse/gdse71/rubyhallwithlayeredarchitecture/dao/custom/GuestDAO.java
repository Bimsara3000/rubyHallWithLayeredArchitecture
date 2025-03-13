package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.SuperDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Guest;

import java.sql.SQLException;

public interface GuestDAO extends CrudDAO<Guest> {
    public String getGuestId(String guestName) throws SQLException, ClassNotFoundException;
    public String getEmail(String guestName) throws SQLException, ClassNotFoundException;
}
