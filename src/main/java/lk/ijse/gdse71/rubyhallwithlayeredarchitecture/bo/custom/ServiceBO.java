package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ServiceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceBO extends SuperBO {
    public ArrayList<ServiceDTO> getAll() throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public String getServices(String serviceId) throws SQLException, ClassNotFoundException;
    public boolean save(ServiceDTO serviceDTO) throws SQLException, ClassNotFoundException;
    public boolean update(ServiceDTO serviceDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String serviceId) throws SQLException, ClassNotFoundException;
    public String getServiceId(String service) throws SQLException, ClassNotFoundException;
}
