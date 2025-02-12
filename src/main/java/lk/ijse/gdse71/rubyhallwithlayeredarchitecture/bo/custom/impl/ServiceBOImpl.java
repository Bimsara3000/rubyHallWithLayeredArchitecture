package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ServiceBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ServiceDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ServiceDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceBOImpl implements ServiceBO {
    ServiceDAO serviceDAO = (ServiceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SERVICE);

    public ArrayList<ServiceDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Service> services = serviceDAO.getAll();
        ArrayList<ServiceDTO> serviceDTOs = new ArrayList<>();

        for (Service service : services) {
            ServiceDTO serviceDTO = new ServiceDTO(
                    service.getServiceId(),
                    service.getDescription(),
                    service.getPrice()
            );
            serviceDTOs.add(serviceDTO);
        }
        return serviceDTOs;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return serviceDAO.getNextId();
    }

    public String getServices(String serviceId) throws SQLException, ClassNotFoundException {
        return serviceDAO.getServices(serviceId);
    }

    public boolean save(ServiceDTO serviceDTO) throws SQLException, ClassNotFoundException {
        return serviceDAO.save(new Service(serviceDTO.getServiceId(),serviceDTO.getDescription(),serviceDTO.getPrice()));
    }

    public boolean update(ServiceDTO serviceDTO) throws SQLException, ClassNotFoundException {
        return serviceDAO.update(new Service(serviceDTO.getServiceId(),serviceDTO.getDescription(),serviceDTO.getPrice()));
    }

    public boolean delete(String serviceId) throws SQLException, ClassNotFoundException {
        return serviceDAO.delete(serviceId);
    }
}
