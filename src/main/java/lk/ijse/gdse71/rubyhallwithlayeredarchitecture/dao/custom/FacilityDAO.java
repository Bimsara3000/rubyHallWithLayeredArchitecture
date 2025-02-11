package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Facility;

import java.sql.SQLException;

public interface FacilityDAO extends CrudDAO<Facility> {
    public String getFacilityId(String facility) throws SQLException, ClassNotFoundException;

    }
