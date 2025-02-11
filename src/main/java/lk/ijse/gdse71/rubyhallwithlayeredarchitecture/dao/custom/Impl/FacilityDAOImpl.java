package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.FacilityDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Facility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacilityDAOImpl implements FacilityDAO {
    public String getName(String facilityId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select description from facility where facilityId = ?", facilityId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
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

    public ArrayList<Facility> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from facility");

        ArrayList<Facility> facilityDTOS = new ArrayList<>();

        while (resultSet.next()) {
            Facility facility = new Facility(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            facilityDTOS.add(facility);
        }
        return facilityDTOS;
    }

    public String getFacilityId(String facility) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select facilityId from facility where description = ?", facility);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean save(Facility facility) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into facility values (?,?,?)",
                facility.getFacilityId(),
                facility.getDescription(),
                facility.getPrice()
        );
    }

    public boolean update(Facility facility) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update facility set description=?,price=? where facilityId=?",
                facility.getDescription(),
                facility.getPrice(),
                facility.getFacilityId()
        );
    }

    public boolean delete(String facilityId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from facility where facilityId = ?",
                facilityId
        );
    }
}
