package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationRoomBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationRoomDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationRoomDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.ReservationRoom;

import java.sql.Connection;
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

    @Override
    public boolean saveReservationRoom(ReservationRoomDTO reservationRoomDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationRoomDAO.saveReservationRoom(new ReservationRoom(
                reservationRoomDTO.getReservationId(),
                reservationRoomDTO.getRoomId(),
                reservationRoomDTO.getStartDate(),
                reservationRoomDTO.getEndDate()),
                connection
        );
    }

    @Override
    public boolean deleteReservationRoom(String resId, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationRoomDAO.deleteReservationRoom(resId,connection);
    }
}
