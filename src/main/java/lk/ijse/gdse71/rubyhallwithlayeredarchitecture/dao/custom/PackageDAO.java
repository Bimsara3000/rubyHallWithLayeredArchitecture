package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Package;

import java.sql.SQLException;

public interface PackageDAO extends CrudDAO<Package> {
    public String getPackageId(String packages) throws SQLException, ClassNotFoundException;
}
