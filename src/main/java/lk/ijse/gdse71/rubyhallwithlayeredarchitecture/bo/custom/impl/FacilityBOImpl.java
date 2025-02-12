package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FacilityBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.FacilityDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FacilityDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Facility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacilityBOImpl implements FacilityBO {
    FacilityDAO facilityDAO = (FacilityDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FACILITY);

    public String getName(String facilityId) throws SQLException, ClassNotFoundException {
        return facilityDAO.getName(facilityId);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return facilityDAO.getNextId();
    }

    public ArrayList<FacilityDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Facility> facilities = facilityDAO.getAll();
        ArrayList<FacilityDTO> facilityDTOs = new ArrayList<>();

        for (Facility facility : facilities) {
            FacilityDTO facilityDTO = new FacilityDTO(
                    facility.getFacilityId(),
                    facility.getDescription(),
                    facility.getPrice()
            );
            facilityDTOs.add(facilityDTO);
        }
        return facilityDTOs;
    }

    public String getFacilityId(String facility) throws SQLException, ClassNotFoundException {
        return facilityDAO.getFacilityId(facility);
    }

    public boolean save(FacilityDTO facilityDTO) throws SQLException, ClassNotFoundException {
        return facilityDAO.save(new Facility(facilityDTO.getFacilityId(),facilityDTO.getDescription(),facilityDTO.getPrice()));
    }

    public boolean update(FacilityDTO facilityDTO) throws SQLException, ClassNotFoundException {
        return facilityDAO.update(new Facility(facilityDTO.getFacilityId(),facilityDTO.getDescription(),facilityDTO.getPrice()));
    }

    public boolean delete(String facilityId) throws SQLException, ClassNotFoundException {
        return facilityDAO.delete(facilityId);
    }
}
