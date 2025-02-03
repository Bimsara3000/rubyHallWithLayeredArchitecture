package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.FloorDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FloorDAOImpl {
    public String getFloor(String floorId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from floor where floorId = ?", floorId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextFloorId() throws SQLException {
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

    public ArrayList<FloorDTO> getAllFloors() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from floor");

        ArrayList<FloorDTO> floorDTOS = new ArrayList<>();

        while (resultSet.next()) {
            FloorDTO floorDTO = new FloorDTO(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            floorDTOS.add(floorDTO);
        }
        return floorDTOS;
    }

    public ArrayList<String> getFloors() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from floor");

        ArrayList<String> floors = new ArrayList<>();

        while (resultSet.next()) {
            floors.add(resultSet.getString(1));
        }
        return floors;
    }

    public String getFloorId(String floor) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select floorId from floor where description = ?", floor);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean saveFloor(FloorDTO floorDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into floor values (?,?)",
                floorDTO.getFloorId(),
                floorDTO.getDescription()
        );
    }

    public boolean updateFloor(FloorDTO floorDTO) throws SQLException {
        return CrudUtil.execute(
                "update floor set description=? where floorId=?",
                floorDTO.getDescription(),
                floorDTO.getFloorId()
        );
    }

    public boolean deletePaymentType(String floorId) throws SQLException {
        return CrudUtil.execute(
                "delete from floor where floorId = ?",
                floorId
        );
    }
}
