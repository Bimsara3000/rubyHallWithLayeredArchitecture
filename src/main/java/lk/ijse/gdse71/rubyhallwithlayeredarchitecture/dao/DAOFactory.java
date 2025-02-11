package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.GuestDAOImpl;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.RoomDAOImpl;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance(DAOType room) {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType{
        GUEST,ROOM,USER
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case GUEST: new GuestDAOImpl();
            case ROOM: new RoomDAOImpl();
            case USER: new UserDAOImpl();
            default: return null;
        }
    }
}
