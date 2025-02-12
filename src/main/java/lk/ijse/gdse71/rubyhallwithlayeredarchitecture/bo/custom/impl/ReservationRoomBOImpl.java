package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationRoomBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationRoomDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationRoomBOImpl implements ReservationRoomBO {
    ReservationRoomDAO reservationRoomDAO = (ReservationRoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVATION_ROOM);

    public String getRoomIds(String reservationId) throws SQLException, ClassNotFoundException {
        return reservationRoomDAO.getRoomIds(reservationId);
    }

    public String getResDate(String reservationId) throws SQLException, ClassNotFoundException {
        return reservationRoomDAO.getResDate(reservationId);
    }

    public ArrayList<String> getAllPossibleRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        return reservationRoomDAO.getAllPossibleRooms(startDate, endDate);
    }
}
