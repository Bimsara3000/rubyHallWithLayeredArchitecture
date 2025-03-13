package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PackageBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PackageDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PackageDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Package;

import java.sql.SQLException;
import java.util.ArrayList;

public class PackageBOImpl implements PackageBO {
    PackageDAO packageDAO = (PackageDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PACKAGE);

    public String getName(String packageId) throws SQLException, ClassNotFoundException {
        return packageDAO.getName(packageId);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return packageDAO.getNextId();
    }

    public ArrayList<PackageDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Package> packages = packageDAO.getAll();
        ArrayList<PackageDTO> packageDTOs = new ArrayList<>();

        for (Package p : packages) {
            PackageDTO packageDTO = new PackageDTO(
                    p.getPackageId(),
                    p.getName(),
                    p.getDescription(),
                    p.getDuration(),
                    p.getPrice(),
                    p.getValidity()
            );
            packageDTOs.add(packageDTO);
        }
        return packageDTOs;
    }

    public boolean update(PackageDTO packageDTO) throws SQLException, ClassNotFoundException {
        return packageDAO.update(new Package(packageDTO.getPackageId(),packageDTO.getName(),packageDTO.getDescription(),packageDTO.getDuration(),packageDTO.getPrice(),packageDTO.getValidity()));
    }

    public boolean save(PackageDTO packageDTO) throws SQLException, ClassNotFoundException {
        return packageDAO.save(new Package(packageDTO.getPackageId(),packageDTO.getName(),packageDTO.getDescription(),packageDTO.getDuration(),packageDTO.getPrice(),packageDTO.getValidity()));
    }

    public boolean delete(String packageId) throws SQLException, ClassNotFoundException {
        return packageDAO.delete(packageId);
    }

    @Override
    public String getPackageId(String packages) throws SQLException, ClassNotFoundException {
        return packageDAO.getPackageId(packages);
    }
}
