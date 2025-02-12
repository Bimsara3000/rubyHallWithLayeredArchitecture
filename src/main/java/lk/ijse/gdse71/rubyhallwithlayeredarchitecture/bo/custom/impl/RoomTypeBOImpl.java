package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomTypeBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.RoomTypeDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomTypeDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.RoomType;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomTypeBOImpl implements RoomTypeBO {
    RoomTypeDAO roomTypeDAO = (RoomTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ROOM_TYPE);

    public String getName(String roomTypeId) throws SQLException, ClassNotFoundException {
        return roomTypeDAO.getName(roomTypeId);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return roomTypeDAO.getNextId();
    }

    public ArrayList<RoomTypeDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<RoomType> roomTypes = roomTypeDAO.getAll();
        ArrayList<RoomTypeDTO> roomTypeDTOs = new ArrayList<>();

        for (RoomType roomType : roomTypes) {
            roomTypeDTOs.add(new RoomTypeDTO(roomType.getRoomTypeId(),roomType.getDescription(),roomType.getPrice()));
        }
        return roomTypeDTOs;
    }

    public ArrayList<String> getRoomTypes() throws SQLException, ClassNotFoundException {
        return roomTypeDAO.getRoomTypes();
    }

    public String getRoomTypeId(String roomType) throws SQLException, ClassNotFoundException {
        return roomTypeDAO.getRoomTypeId(roomType);
    }
}
