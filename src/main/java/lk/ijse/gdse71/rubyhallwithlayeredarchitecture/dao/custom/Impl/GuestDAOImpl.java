package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.GuestDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuestDAOImpl implements GuestDAO {
    public ArrayList<Guest> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from guest");

        ArrayList<Guest> guests = new ArrayList<>();

        while (resultSet.next()) {
            Guest guest = new Guest(
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

    public boolean update(Guest guest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update guest set name=?,email=?,phoneNo=? where guestId=?",
                guest.getName(),
                guest.getEmail(),
                guest.getPhoneNo(),
                guest.getGuestId()
        );
    }

    public boolean delete(String guestId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from guest where guestId = ?",
                guestId
        );
    }

    public String getName(String guestId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select name from guest where guestId = ?", guestId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public String getGuestId(String guestName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select guestId from guest where name = ?", guestName);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public String getEmail(String guestName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select email from guest where name = ?", guestName);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
