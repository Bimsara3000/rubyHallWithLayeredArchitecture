package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.QueryBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.QueryDAOImpl;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.QueryDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryBOImpl implements QueryBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public String getPaymentAmount(String reservationId) throws SQLException, ClassNotFoundException {
        return queryDAO.getPaymentAmount(reservationId);
    }

    @Override
    public ArrayList<String> getAllAvailableRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        return queryDAO.getAllAvailableRooms(startDate,endDate);
    }

    @Override
    public ArrayList<String> searchAllAvailableRooms(String startDate, String endDate, String roomTypeId, String floorId) throws SQLException, ClassNotFoundException {
        return queryDAO.searchAllAvailableRooms(startDate,endDate,roomTypeId,floorId);
    }
}
