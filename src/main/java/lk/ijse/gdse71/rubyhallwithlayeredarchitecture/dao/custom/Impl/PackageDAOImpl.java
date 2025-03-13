package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PackageDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageDAOImpl implements PackageDAO {
    public String getName(String packageId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select name from package where packageId = ?", packageId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
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

    public ArrayList<Package> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from package");

        ArrayList<Package> packageDTOS = new ArrayList<>();

        while (resultSet.next()) {
            Package p1 = new Package(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            );
            packageDTOS.add(p1);
        }
        return packageDTOS;
    }

    public boolean update(Package p1) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update package set name=?,description=?,duration=?,price=?,validity=? where packageId=?",
                p1.getName(),
                p1.getDescription(),
                p1.getDuration(),
                p1.getPrice(),
                p1.getValidity(),
                p1.getPackageId()
        );
    }

    public boolean save(Package p1) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into package values (?,?,?,?,?,?)",
                p1.getPackageId(),
                p1.getName(),
                p1.getDescription(),
                p1.getDuration(),
                p1.getPrice(),
                p1.getValidity()
        );
    }

    public boolean delete(String packageId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from package where packageId = ?",
                packageId
        );
    }

    @Override
    public String getPackageId(String packages) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select packageId from package where name = ?", packages);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
