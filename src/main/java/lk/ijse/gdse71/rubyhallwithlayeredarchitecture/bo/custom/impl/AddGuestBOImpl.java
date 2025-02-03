package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.AddGuestBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddGuestBOImpl implements AddGuestBO {
    public String getNextId() throws SQLException, ClassNotFoundException {

    }

    public boolean save(GuestDTO guestDTO) throws SQLException {
        Guest guest = new Guest();
    }
}
