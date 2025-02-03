package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.RoomFacilityDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomFacilityDAOImpl {
    public String getFacilities(String roomId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select facilityId from roomFacility where roomId = ?", roomId);

        FacilityDAOImpl facilityModel = new FacilityDAOImpl();
        StringBuilder facilities = new StringBuilder();

        while (resultSet.next()){
            String facility = facilityModel.getFacilityName(resultSet.getString(1));
            facilities.append(facility);
            facilities.append(",");
        }

        if (!facilities.isEmpty()) {
            facilities.deleteCharAt(facilities.length() - 1);
        }

        return facilities.toString();
    }

    public boolean save(ArrayList<RoomFacilityDTO> roomFacilities) throws SQLException {
        boolean result = true;

        for (RoomFacilityDTO roomFacility : roomFacilities) {
            boolean isSaved = CrudUtil.execute(
                    "insert into roomFacility values (?,?)",
                    roomFacility.getRoomId(),
                    roomFacility.getFacilityId()
            );

            if (!isSaved) {
                result = false;
            }
        }

        return result;
    }

    public boolean deleteFacilities(String roomId) throws SQLException {
        return CrudUtil.execute(
                "delete from roomFacility where roomId = ?",
                roomId
        );
    }
}
