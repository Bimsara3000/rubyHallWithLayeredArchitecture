package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.GuestDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuestDAOImpl implements GuestDAO {
    public ArrayList<GuestDTO> getAllGuests() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from guest");

        ArrayList<GuestDTO> guests = new ArrayList<>();

        while (resultSet.next()) {
            GuestDTO guest = new GuestDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );
            guests.add(guest);
        }
        return guests;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select guestId from guest order by guestId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("G%03d", newIdIndex);
        }
        return "G001";
    }

    public boolean save(Guest guest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into guest values (?,?,?,?)",
                guest.getGuestId(),
                guest.getName(),
                guest.getEmail(),
                guest.getPhoneNo()
        );
    }

    public boolean updateGuest(GuestDTO guestDTO) throws SQLException {
        return CrudUtil.execute(
                "update guest set name=?,email=?,phoneNo=? where guestId=?",
                guestDTO.getName(),
                guestDTO.getEmail(),
                guestDTO.getPhoneNo(),
                guestDTO.getGuestId()
        );
    }

    public boolean deleteGuest(String guestId) throws SQLException {
        return CrudUtil.execute(
                "delete from guest where guestId = ?",
                guestId
        );
    }

    public String getGuestName(String guestId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select name from guest where guestId = ?", guestId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
