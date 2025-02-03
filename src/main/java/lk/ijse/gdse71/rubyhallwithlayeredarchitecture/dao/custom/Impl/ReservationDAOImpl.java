package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.ReservationDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAOImpl {

    public ArrayList<ReservationDTO> getAllReservations() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from reservation");

        ArrayList<ReservationDTO> reservations = new ArrayList<>();

        while (resultSet.next()) {
            ReservationDTO reservation = new ReservationDTO(
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

    public String getNextReservationId() throws SQLException {
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

    public String getGuestId(String reservationId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select guestId from reservation where reservationId = ?", reservationId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
