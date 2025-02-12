package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationService;

import java.sql.SQLException;

public interface ReservationServiceDAO extends CrudDAO<ReservationService> {
    public String getServiceId(String reservationId) throws SQLException, ClassNotFoundException;
}
