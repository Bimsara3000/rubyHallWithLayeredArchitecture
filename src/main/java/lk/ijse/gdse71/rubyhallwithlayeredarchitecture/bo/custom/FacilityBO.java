package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FacilityDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FacilityBO extends SuperBO {
    public String getName(String facilityId) throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<FacilityDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getFacilityId(String facility) throws SQLException, ClassNotFoundException;
    public boolean save(FacilityDTO facilityDTO) throws SQLException, ClassNotFoundException;
    public boolean update(FacilityDTO facilityDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String facilityId) throws SQLException, ClassNotFoundException;
}
