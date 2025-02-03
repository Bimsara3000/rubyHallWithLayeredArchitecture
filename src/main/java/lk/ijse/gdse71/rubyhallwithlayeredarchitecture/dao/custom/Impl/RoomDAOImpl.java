package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.RoomDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDAOImpl {
    public ArrayList<RoomDTO> getAllRooms() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from room");

        ArrayList<RoomDTO> rooms = new ArrayList<>();

        while (resultSet.next()) {
            RoomDTO room = new RoomDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            rooms.add(room);
        }
        return rooms;
    }

    public String getNextRoomId() throws SQLException {
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

    public boolean saveRoom(RoomDTO roomDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into room values (?,?,?,?)",
                roomDTO.getRoomId(),
                roomDTO.getRoomTypeId(),
                roomDTO.getFloorId(),
                roomDTO.getState()
        );
    }

    public boolean updateRoom(RoomDTO roomDTO) throws SQLException {
        return CrudUtil.execute(
                "update room set roomTypeID=?,floorId=?,state=? where roomId=?",
                roomDTO.getRoomTypeId(),
                roomDTO.getFloorId(),
                roomDTO.getState(),
                roomDTO.getRoomId()
        );
    }

    public boolean deleteRoom(String roomId) throws SQLException {
        return CrudUtil.execute(
                "delete from room where roomId = ?",
                roomId
        );
    }

    public ArrayList<String> getRooms(String rId, String fId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select roomId from room where roomTypeId = 'rId' and floorId = 'fId'");

        ArrayList<String> rooms = new ArrayList<>();

        while (resultSet.next()) {
            rooms.add(resultSet.getString(1));
        }

        return rooms;
    }

    public ArrayList<String> getAllAvailableRooms() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select roomId from room where state = 'Available'");

        ArrayList<String> rooms = new ArrayList<>();

        while (resultSet.next()) {
            rooms.add(resultSet.getString(1));
        }

        return rooms;
    }
}
