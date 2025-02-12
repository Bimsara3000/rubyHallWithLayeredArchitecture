package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ServiceDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDAOImpl implements ServiceDAO {
    public ArrayList<Service> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from service");

        ArrayList<Service> serviceDTOS = new ArrayList<>();

        while (resultSet.next()) {
            Service service = new Service(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
            serviceDTOS.add(service);
        }
        return serviceDTOS;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
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

    public String getServices(String serviceId) throws SQLException, ClassNotFoundException {
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

    public boolean save(Service service) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into service values (?,?,?)",
                service.getServiceId(),
                service.getDescription(),
                service.getPrice()
        );
    }

    public boolean update(Service service) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update service set description=?,price=? where serviceId=?",
                service.getDescription(),
                service.getPrice(),
                service.getServiceId()
        );
    }

    public boolean delete(String serviceId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from service where serviceId = ?",
                serviceId
        );
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }
}
