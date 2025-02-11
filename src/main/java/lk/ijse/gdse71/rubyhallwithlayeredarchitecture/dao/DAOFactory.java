package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance(DAOType room) {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType{
        GUEST,ROOM,USER,SERVICE,FACILITY,FLOOR,JOB_ROLE,PACKAGE,PAYMENT,PAYMENT_TYPE,PRICE_FLUC,QUERY,RESERVATION,RESERVATION_ROOM,RESERVATION_SERVICE,ROOM_TYPE
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case GUEST: new GuestDAOImpl();
            case ROOM: new RoomDAOImpl();
            case USER: new UserDAOImpl();
            case SERVICE: new ServiceDAOImpl();
            case FACILITY: new FacilityDAOImpl();
            case FLOOR: new FloorDAOImpl();
            case JOB_ROLE: new JobRoleDAOImpl();
            case PACKAGE: new PackageDAOImpl();
            case PAYMENT: new PaymentDAOImpl();
            case PAYMENT_TYPE: new PaymentTypeDAOImpl();
            case PRICE_FLUC: new PriceFlucDAOImpl();
            case QUERY: new QueryDAOImpl();
            case RESERVATION: new RoomDAOImpl();
            case RESERVATION_ROOM: new ReservationRoomDAOImpl();
            case RESERVATION_SERVICE: new ReservationServiceDAOImpl();
            case ROOM_TYPE: new RoomTypeDAOImpl();
            default: return null;
        }
    }
}
