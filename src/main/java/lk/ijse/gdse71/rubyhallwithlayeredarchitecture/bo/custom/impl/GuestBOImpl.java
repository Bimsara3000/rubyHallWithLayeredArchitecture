package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.GuestBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.GuestDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Guest;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuestBOImpl implements GuestBO {
    GuestDAO guestDAO = (GuestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.GUEST);

    public ArrayList<GuestDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Guest> guests = guestDAO.getAll();
        ArrayList<GuestDTO> guestDTOS = new ArrayList<>();
        for (Guest guest : guests) {
            GuestDTO guestDTO = new GuestDTO(
                    guest.getGuestId(),
                    guest.getName(),
                    guest.getEmail(),
                    guest.getPhoneNo()
            );
            guestDTOS.add(guestDTO);
        }
        return guestDTOS;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return guestDAO.getNextId();
    }

    public boolean save(GuestDTO guestDTO) throws SQLException, ClassNotFoundException {
        return guestDAO.save(new Guest(guestDTO.getGuestId(),guestDTO.getName(),guestDTO.getEmail(),guestDTO.getPhoneNo()));
    }

    public boolean update(GuestDTO guestDTO) throws SQLException, ClassNotFoundException {
        return guestDAO.update(new Guest(guestDTO.getGuestId(),guestDTO.getName(),guestDTO.getEmail(),guestDTO.getPhoneNo()));
    }

    public boolean delete(String guestId) throws SQLException, ClassNotFoundException {
        return guestDAO.delete(guestId);
    }

    public String getName(String guestId) throws SQLException, ClassNotFoundException {
        return guestDAO.getName(guestId);
    }
}
