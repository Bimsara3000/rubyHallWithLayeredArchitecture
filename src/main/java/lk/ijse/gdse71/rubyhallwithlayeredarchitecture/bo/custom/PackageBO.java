package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PackageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PackageBO extends SuperBO {
    public String getName(String packageId) throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<PackageDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(PackageDTO packageDTO) throws SQLException, ClassNotFoundException;
    public boolean save(PackageDTO packageDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String packageId) throws SQLException, ClassNotFoundException;
    public String getPackageId(String packages) throws SQLException, ClassNotFoundException;
}
