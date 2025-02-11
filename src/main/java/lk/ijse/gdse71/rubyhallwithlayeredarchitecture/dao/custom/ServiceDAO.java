package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Service;

import java.sql.SQLException;

public interface ServiceDAO extends CrudDAO<Service> {
    public String getServices(String serviceId) throws SQLException, ClassNotFoundException;
}
