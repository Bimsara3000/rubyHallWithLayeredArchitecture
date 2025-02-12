package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {
    public ArrayList<Reservation> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from reservation");

        ArrayList<Reservation> reservations = new ArrayList<>();

        while (resultSet.next()) {
            Reservation reservation = new Reservation(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            reservations.add(reservation);
        }
        return reservations;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select reservationId from reservation order by reservationId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("RE%03d", newIdIndex);
        }
        return "RE001";
    }

    @Override
    public boolean save(Reservation dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Reservation dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }

    public String getGuestId(String reservationId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select guestId from reservation where reservationId = ?", reservationId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
