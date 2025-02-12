package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.BOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FacilityBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.RoomFacilityDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomFacility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomFacilityDAOImpl implements RoomFacilityDAO {
    public String getFacilities(String roomId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select facilityId from roomFacility where roomId = ?", roomId);

        FacilityBO facilityBO = (FacilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.FACILITY);
        StringBuilder facilities = new StringBuilder();

        while (resultSet.next()){
            String facility = facilityBO.getName(resultSet.getString(1));
            facilities.append(facility);
            facilities.append(",");
        }

        if (!facilities.isEmpty()) {
            facilities.deleteCharAt(facilities.length() - 1);
        }

        return facilities.toString();
    }

    public boolean deleteFacilities(String roomId, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeTransaction("delete from roomFacility where roomId = ?",connection,roomId);
    }

    @Override
    public ArrayList<RoomFacility> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean save(RoomFacility dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(RoomFacility dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean delete(String roomId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from roomFacility where roomId = ?",
                roomId
        );
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }

    public boolean saveFacilities(ArrayList<RoomFacility> roomFacilities,Connection connection) throws SQLException, ClassNotFoundException {
        boolean result = true;

        for (RoomFacility roomFacility : roomFacilities) {
            boolean isSaved = CrudUtil.executeTransaction(
                    "insert into roomFacility values (?,?)",
                    connection,
                    roomFacility.getRoomId(),
                    roomFacility.getFacilityId()
            );

            if (!isSaved) {
                result = false;
            }
        }

        return result;
    }
}
