package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationService;

import java.sql.Connection;
import java.sql.SQLException;

public interface ReservationServiceDAO extends CrudDAO<ReservationService> {
    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException;
    public boolean saveReservationService(ReservationService reservationService, Connection connection) throws SQLException, ClassNotFoundException;
    public boolean deleteReservationService(String resId, Connection connection) throws SQLException, ClassNotFoundException;
    public String getDuration(String reservationId) throws SQLException, ClassNotFoundException;
}
