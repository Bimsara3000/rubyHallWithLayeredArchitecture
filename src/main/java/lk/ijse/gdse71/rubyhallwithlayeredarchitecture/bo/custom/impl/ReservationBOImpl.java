package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVATION);

    public ArrayList<ReservationDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Reservation> reservations = reservationDAO.getAll();
        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();

        for (Reservation reservation : reservations) {
            reservationDTOS.add(new ReservationDTO(reservation.getReservationId(),reservation.getUserId(),reservation.getGuestId(),reservation.getPackageId(),reservation.getGuestCount(),reservation.getDate(),reservation.getDescription()));
        }
        return reservationDTOS;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return reservationDAO.getNextId();
    }

    public String getGuestId(String reservationId) throws SQLException, ClassNotFoundException {
        return reservationDAO.getGuestId(reservationId);
    }
}
