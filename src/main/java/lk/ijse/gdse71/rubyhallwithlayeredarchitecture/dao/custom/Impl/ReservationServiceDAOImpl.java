package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationServiceDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationServiceDAOImpl implements ReservationServiceDAO {
    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select serviceId from reservationService where reservationId = ?", reservationId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<ReservationService> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean save(ReservationService dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ReservationService dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }
}
