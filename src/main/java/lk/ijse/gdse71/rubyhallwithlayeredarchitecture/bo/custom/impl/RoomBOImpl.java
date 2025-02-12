package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.RoomBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.RoomDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.GuestDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.RoomDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Guest;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ROOM);

    public ArrayList<RoomDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Room> rooms = roomDAO.getAll();
        ArrayList<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : rooms) {
            RoomDTO roomDTO = new RoomDTO(
                    room.getRoomId(),
                    room.getRoomTypeId(),
                    room.getFloorId(),
                    room.getState()
            );
            roomDTOS.add(roomDTO);
        }
        return roomDTOS;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return roomDAO.getNextId();
    }

    public boolean save(RoomDTO roomDTO) throws SQLException, ClassNotFoundException {
        return roomDAO.save(new Room(roomDTO.getRoomId(),roomDTO.getRoomTypeId(),roomDTO.getFloorId(),roomDTO.getState()));
    }

    public boolean update(RoomDTO roomDTO) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(roomDTO.getRoomId(),roomDTO.getRoomTypeId(),roomDTO.getFloorId(),roomDTO.getState()));
    }

    public boolean delete(String roomId) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(roomId);
    }

    public ArrayList<String> getRooms(String rId, String fId) throws SQLException, ClassNotFoundException {
        return roomDAO.getRooms(rId,fId);
    }

    public ArrayList<String> getAllAvailableRooms() throws SQLException, ClassNotFoundException {
        return roomDAO.getAllAvailableRooms();
    }

    public boolean deleteRoom(String roomId, Connection connection) throws SQLException, ClassNotFoundException {
        return roomDAO.deleteRoom(roomId,connection);
    }

    public boolean updateRoom(RoomDTO room, Connection connection) throws SQLException, ClassNotFoundException {
        return roomDAO.updateRoom(new Room(room.getRoomId(),room.getRoomTypeId(),room.getFloorId(),room.getState()),connection);
    }
}
