package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationRoomDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationRoom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationRoomDAOImpl implements ReservationRoomDAO {
    public String getRoomIds(String reservationId) throws SQLException, ClassNotFoundException {
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

    public String getResDate(String reservationId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select startDate,endDate from reservationRoom where reservationId = ?", reservationId);

        if (resultSet.next()){
            return resultSet.getString(1)+" : "+resultSet.getString(2);
        }

        return null;
    }

    public ArrayList<String> getAllPossibleRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select roomId from reservationRoom where startDate > ? and endDate < ?", endDate, startDate);

        ArrayList<String> rooms = new ArrayList<>();

        while (resultSet.next()) {
            rooms.add(resultSet.getString(1));
        }

        return rooms;
    }

    @Override
    public boolean saveReservationRoom(ReservationRoom reservationRoom, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeTransaction(
                "insert into reservationRoom values (?,?,?,?)",
                connection,
                reservationRoom.getReservationId(),
                reservationRoom.getRoomId(),
                reservationRoom.getStartDate(),
                reservationRoom.getEndDate()
        );
    }

    @Override
    public boolean deleteReservationRoom(String resId, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeTransaction("delete from reservationRoom where reservationId = ?",connection,resId);
    }

    @Override
    public ArrayList<ReservationRoom> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean save(ReservationRoom dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ReservationRoom dto) throws SQLException, ClassNotFoundException {
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
}
