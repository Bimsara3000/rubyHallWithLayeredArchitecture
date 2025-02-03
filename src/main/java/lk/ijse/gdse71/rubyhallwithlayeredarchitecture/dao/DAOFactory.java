package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.GuestDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType{
        GUEST
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case GUEST: new GuestDAOImpl();
            default: return null;
        }
    }
}
