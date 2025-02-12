package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomFacilityBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.RoomFacilityDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomFacilityDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomFacility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomFacilityBOImpl implements RoomFacilityBO {
    RoomFacilityDAO roomFacilityDAO = (RoomFacilityDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ROOM_FACILITY);

    public String getFacilities(String roomId) throws SQLException, ClassNotFoundException {
        return roomFacilityDAO.getFacilities(roomId);
    }

    @Override
    public boolean saveFacilities(ArrayList<RoomFacilityDTO> roomFacilities, Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<RoomFacility> facilities = new ArrayList<>();

        for (RoomFacilityDTO roomFacilityDTO : roomFacilities) {
            facilities.add(new RoomFacility(roomFacilityDTO.getRoomId(), roomFacilityDTO.getFacilityId()));
        }

        if (facilities.size() > 0) {
            return roomFacilityDAO.saveFacilities(facilities,connection);
        }
        return false;
    }

    public boolean delete(String roomId) throws SQLException, ClassNotFoundException {
        return roomFacilityDAO.delete(roomId);
    }

    public boolean deleteFacilities(String roomId, Connection connection) throws SQLException, ClassNotFoundException {
        return roomFacilityDAO.deleteFacilities(roomId, connection);
    }
}
