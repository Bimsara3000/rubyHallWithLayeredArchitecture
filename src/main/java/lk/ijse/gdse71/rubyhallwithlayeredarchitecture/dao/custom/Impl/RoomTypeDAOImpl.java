package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.RoomTypeDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomTypeDAOImpl implements RoomTypeDAO {
    public String getName(String roomTypeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select description from roomType where roomTypeId = ?", roomTypeId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
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

    @Override
    public boolean save(RoomType roomType) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into roomType values (?,?,?)",
                roomType.getRoomTypeId(),
                roomType.getDescription(),
                roomType.getPrice()
        );
    }

    @Override
    public boolean update(RoomType roomType) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update roomType set description=?,price=? where roomTypeId=?",
                roomType.getDescription(),
                roomType.getPrice(),
                roomType.getRoomTypeId()
        );
    }

    @Override
    public boolean delete(String roomTypeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from roomType where roomTypeId = ?",
                roomTypeId
        );
    }

    public ArrayList<RoomType> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from roomType");

        ArrayList<RoomType> roomTypes = new ArrayList<>();

        while (resultSet.next()) {
            RoomType roomType = new RoomType(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            roomTypes.add(roomType);
        }
        return roomTypes;
    }

    public ArrayList<String> getRoomTypes() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select description from roomType");

        ArrayList<String> roomTypes = new ArrayList<>();

        while (resultSet.next()) {
            roomTypes.add(resultSet.getString(1));
        }
        return roomTypes;
    }

    public String getRoomTypeId(String roomType) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select roomTypeId from roomType where description = ?", roomType);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
