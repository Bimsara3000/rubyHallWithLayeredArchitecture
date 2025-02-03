package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.RoomTypeDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomTypeDAOImpl {
    public String getRoomType(String roomTypeId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from roomType where roomTypeId = ?", roomTypeId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextRoomTypeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select roomTypeId from roomType order by roomTypeId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("RT%03d", newIdIndex);
        }
        return "RT001";
    }

    public ArrayList<RoomTypeDTO> getAllRoomTypes() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from roomType");

        ArrayList<RoomTypeDTO> roomTypeDTOS = new ArrayList<>();

        while (resultSet.next()) {
            RoomTypeDTO roomTypeDTO = new RoomTypeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            roomTypeDTOS.add(roomTypeDTO);
        }
        return roomTypeDTOS;
    }

    public ArrayList<String> getRoomTypes() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from roomType");

        ArrayList<String> roomTypes = new ArrayList<>();

        while (resultSet.next()) {
            roomTypes.add(resultSet.getString(1));
        }
        return roomTypes;
    }

    public String getRoomTypeId(String roomType) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select roomTypeId from roomType where description = ?", roomType);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
