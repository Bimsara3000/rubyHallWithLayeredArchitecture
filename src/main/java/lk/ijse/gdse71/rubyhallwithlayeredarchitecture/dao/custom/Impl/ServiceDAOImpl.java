package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.ServiceDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDAOImpl {
    public ArrayList<ServiceDTO> getAllServices() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from service");

        ArrayList<ServiceDTO> serviceDTOS = new ArrayList<>();

        while (resultSet.next()) {
            ServiceDTO serviceDTO = new ServiceDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            serviceDTOS.add(serviceDTO);
        }
        return serviceDTOS;
    }

    public String getNextServiceId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select serviceId from service order by serviceId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public String getServices(String serviceId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from service where serviceId = ?", serviceId);

        StringBuilder services = new StringBuilder();

        while (resultSet.next()){
            services.append(resultSet.getString("description"));
            services.append(",");
        }
        if (!services.isEmpty()) {
            services.deleteCharAt(services.length() - 1);
        }

        return services.toString();
    }

    public boolean saveService(ServiceDTO serviceDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into service values (?,?,?)",
                serviceDTO.getServiceId(),
                serviceDTO.getDescription(),
                serviceDTO.getPrice()
        );
    }

    public boolean updateService(ServiceDTO serviceDTO) throws SQLException {
        return CrudUtil.execute(
                "update service set description=?,price=? where serviceId=?",
                serviceDTO.getDescription(),
                serviceDTO.getPrice(),
                serviceDTO.getServiceId()
        );
    }

    public boolean deleteService(String serviceId) throws SQLException {
        return CrudUtil.execute(
                "delete from service where serviceId = ?",
                serviceId
        );
    }
}
