package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.RoomDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDAOImpl implements RoomDAO {
    public ArrayList<Room> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from room");

        ArrayList<Room> rooms = new ArrayList<>();

        while (resultSet.next()) {
            Room room = new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            rooms.add(room);
        }
        return rooms;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select roomId from room order by roomId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }
        return "R001";
    }

    public boolean save(Room room) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into room values (?,?,?,?)",
                room.getRoomId(),
                room.getRoomTypeId(),
                room.getFloorId(),
                room.getState()
        );
    }

    public boolean update(Room room) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update room set roomTypeID=?,floorId=?,state=? where roomId=?",
                room.getRoomTypeId(),
                room.getFloorId(),
                room.getState(),
                room.getRoomId()
        );
    }

    public boolean delete(String roomId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from room where roomId = ?",
                roomId
        );
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }

    public ArrayList<String> getRooms(String rId, String fId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select roomId from room where roomTypeId = 'rId' and floorId = 'fId'");

        ArrayList<String> rooms = new ArrayList<>();

        while (resultSet.next()) {
            rooms.add(resultSet.getString(1));
        }

        return rooms;
    }

    public ArrayList<String> getAllAvailableRooms() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select roomId from room where state = 'Available'");

        ArrayList<String> rooms = new ArrayList<>();

        while (resultSet.next()) {
            rooms.add(resultSet.getString(1));
        }

        return rooms;
    }
}
