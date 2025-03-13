package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationServiceDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationService;

import java.sql.Connection;
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
    public boolean saveReservationService(ReservationService reservationService, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeTransaction(
                "insert into reservationService values (?,?,?)",
                connection,
                reservationService.getServiceId(),
                reservationService.getReservationId(),
                reservationService.getDuration()
        );
    }

    @Override
    public boolean deleteReservationService(String resId, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeTransaction("delete from reservationService where reservationId = ?",connection,resId);
    }

    @Override
    public String getDuration(String reservationId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select duration from reservationService where reservationId = ?", reservationId);

        if (resultSet.next()){
            return Integer.toString(resultSet.getInt(1));
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
