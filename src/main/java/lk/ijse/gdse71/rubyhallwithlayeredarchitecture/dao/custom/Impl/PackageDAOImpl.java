package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.PackageDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageDAOImpl {

    public String getPackageName(String packageId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select name from package where packageId = ?", packageId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextPackageId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select packageId from package order by packageId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public ArrayList<PackageDTO> getAllPackages() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from package");

        ArrayList<PackageDTO> packageDTOS = new ArrayList<>();

        while (resultSet.next()) {
            PackageDTO packageDTO = new PackageDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            );
            packageDTOS.add(packageDTO);
        }
        return packageDTOS;
    }

    public boolean updatePackage(PackageDTO packageDTO) throws SQLException {
        return CrudUtil.execute(
                "update package set name=?,description=?,duration=?,price=?,validity=? where packageId=?",
                packageDTO.getName(),
                packageDTO.getDescription(),
                packageDTO.getDuration(),
                packageDTO.getPrice(),
                packageDTO.getValidity(),
                packageDTO.getPackageId()
        );
    }

    public boolean savePackage(PackageDTO packageDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into package values (?,?,?,?,?,?)",
                packageDTO.getPackageId(),
                packageDTO.getName(),
                packageDTO.getDescription(),
                packageDTO.getDuration(),
                packageDTO.getPrice(),
                packageDTO.getValidity()
        );
    }

    public boolean deletePackage(String packageId) throws SQLException {
        return CrudUtil.execute(
                "delete from package where packageId = ?",
                packageId
        );
    }
}
