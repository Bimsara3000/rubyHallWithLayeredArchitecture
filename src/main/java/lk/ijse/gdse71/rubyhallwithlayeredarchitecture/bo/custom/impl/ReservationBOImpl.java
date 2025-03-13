package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.ReservationBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.ReservationDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.ReservationDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Reservation;

import java.sql.Connection;
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

    @Override
    public String getReservationId(String guestId) throws SQLException, ClassNotFoundException {
        return reservationDAO.getReservationId(guestId);
    }

    @Override
    public boolean saveReservation(ReservationDTO reservationDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationDAO.saveReservation(new Reservation(
                reservationDTO.getReservationId(),
                reservationDTO.getUserId(),
                reservationDTO.getGuestId(),
                reservationDTO.getPackageId(),
                reservationDTO.getGuestCount(),
                reservationDTO.getDate(),
                reservationDTO.getDescription()),
                connection
        );
    }

    @Override
    public boolean updateReservation(ReservationDTO reservationDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationDAO.updateReservation(new Reservation(
                        reservationDTO.getReservationId(),
                        reservationDTO.getUserId(),
                        reservationDTO.getGuestId(),
                        reservationDTO.getPackageId(),
                        reservationDTO.getGuestCount(),
                        reservationDTO.getDate(),
                        reservationDTO.getDescription()),
                connection
        );
    }

    @Override
    public boolean deleteReservation(String reservationId, Connection connection) throws SQLException, ClassNotFoundException {
        return reservationDAO.deleteReservation(reservationId,connection);
    }
}
