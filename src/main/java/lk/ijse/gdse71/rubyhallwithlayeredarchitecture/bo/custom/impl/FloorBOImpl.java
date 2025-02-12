package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.FloorBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.FloorDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.FloorDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.Floor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FloorBOImpl implements FloorBO {
    FloorDAO floorDAO = (FloorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.FLOOR);

    public String getName(String floorId) throws SQLException, ClassNotFoundException {
        return floorDAO.getName(floorId);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return floorDAO.getNextId();
    }

    public ArrayList<FloorDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Floor> floors = floorDAO.getAll();
        ArrayList<FloorDTO> floorDTOs = new ArrayList<>();

        for (Floor floor : floors) {
            FloorDTO floorDTO = new FloorDTO(
                    floor.getFloorId(),
                    floor.getDescription()
            );
            floorDTOs.add(floorDTO);
        }
        return floorDTOs;
    }

    public ArrayList<String> getFloors() throws SQLException, ClassNotFoundException {
        return floorDAO.getFloors();
    }

    public String getFloorId(String floor) throws SQLException, ClassNotFoundException {
        return floorDAO.getFloorId(floor);
    }

    public boolean save(FloorDTO floorDTO) throws SQLException, ClassNotFoundException {
        return floorDAO.save(new Floor(floorDTO.getFloorId(),floorDTO.getDescription()));
    }

    public boolean update(FloorDTO floorDTO) throws SQLException, ClassNotFoundException {
        return floorDAO.update(new Floor(floorDTO.getFloorId(),floorDTO.getDescription()));
    }

    public boolean delete(String floorId) throws SQLException, ClassNotFoundException {
        return floorDAO.delete(floorId);
    }
}
