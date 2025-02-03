package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationServiceDAOImpl {
    public String getServiceId(String reservationId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select serviceId from reservationService where reservationId = ?", reservationId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
