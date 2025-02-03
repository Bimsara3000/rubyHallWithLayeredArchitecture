package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationRoomDAOImpl {
    public String getRoomIds(String reservationId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select roomId from reservationRoom where reservationId = ?", reservationId);

        StringBuilder roomId = new StringBuilder();

        while (resultSet.next()){
            roomId.append(resultSet.getString("roomId"));
            roomId.append(",");
        }
        if (!roomId.isEmpty()) {
            roomId.deleteCharAt(roomId.length() - 1);
        }

        return roomId.toString();
    }

    public String getResDate(String reservationId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select startDate,endDate from reservationRoom where reservationId = ?", reservationId);

        if (resultSet.next()){
            return resultSet.getString(1)+" : "+resultSet.getString(2);
        }

        return null;
    }

    public ArrayList<String> getAllPossibleRooms(String startDate, String endDate) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select roomId from reservationRoom where startDate > ? and endDate < ?", endDate, startDate);

        ArrayList<String> rooms = new ArrayList<>();

        while (resultSet.next()) {
            rooms.add(resultSet.getString(1));
        }

        return rooms;
    }
}
