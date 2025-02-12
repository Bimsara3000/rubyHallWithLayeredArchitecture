package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.FloorDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Floor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FloorDAOImpl implements FloorDAO {
    public String getName(String floorId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select description from floor where floorId = ?", floorId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select floorId from floor order by floorId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("F%03d", newIdIndex);
        }
        return "F001";
    }

    public ArrayList<Floor> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from floor");

        ArrayList<Floor> floors = new ArrayList<>();

        while (resultSet.next()) {
            Floor floor = new Floor(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            floors.add(floor);
        }
        return floors;
    }

    public ArrayList<String> getFloors() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select description from floor");

        ArrayList<String> floors = new ArrayList<>();

        while (resultSet.next()) {
            floors.add(resultSet.getString(1));
        }
        return floors;
    }

    public String getFloorId(String floor) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select floorId from floor where description = ?", floor);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean save(Floor floor) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into floor values (?,?)",
                floor.getFloorId(),
                floor.getDescription()
        );
    }

    public boolean update(Floor floor) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update floor set description=? where floorId=?",
                floor.getDescription(),
                floor.getFloorId()
        );
    }

    public boolean delete(String floorId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from floor where floorId = ?",
                floorId
        );
    }
}
