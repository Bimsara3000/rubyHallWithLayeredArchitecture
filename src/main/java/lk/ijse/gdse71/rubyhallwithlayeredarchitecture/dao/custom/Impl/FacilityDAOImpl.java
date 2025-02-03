package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.FacilityDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacilityDAOImpl {
    public String getFacilityName(String facilityId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from facility where facilityId = ?", facilityId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextFacilityId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select facilityId from facility order by facilityId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("FC%03d", newIdIndex);
        }
        return "FC001";
    }

    public ArrayList<FacilityDTO> getAllFacilities() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from facility");

        ArrayList<FacilityDTO> facilityDTOS = new ArrayList<>();

        while (resultSet.next()) {
            FacilityDTO facilityDTO = new FacilityDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            facilityDTOS.add(facilityDTO);
        }
        return facilityDTOS;
    }

    public String getFacilityId(String facility) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select facilityId from facility where description = ?", facility);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean saveFacility(FacilityDTO facilityDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into facility values (?,?,?)",
                facilityDTO.getFacilityId(),
                facilityDTO.getDescription(),
                facilityDTO.getPrice()
        );
    }

    public boolean updateFacility(FacilityDTO facilityDTO) throws SQLException {
        return CrudUtil.execute(
                "update facility set description=?,price=? where facilityId=?",
                facilityDTO.getDescription(),
                facilityDTO.getPrice(),
                facilityDTO.getFacilityId()
        );
    }

    public boolean deleteFacility(String facilityId) throws SQLException {
        return CrudUtil.execute(
                "delete from facility where facilityId = ?",
                facilityId
        );
    }
}
