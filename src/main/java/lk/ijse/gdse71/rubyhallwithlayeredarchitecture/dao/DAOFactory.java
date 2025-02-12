package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType{
        GUEST,ROOM,USER,SERVICE,FACILITY,FLOOR,JOB_ROLE,PACKAGE,PAYMENT,PAYMENT_TYPE,PRICE_FLUC,QUERY,RESERVATION,RESERVATION_ROOM,RESERVATION_SERVICE,ROOM_TYPE,ROOM_FACILITY
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case GUEST: return new GuestDAOImpl();
            case ROOM: return new RoomDAOImpl();
            case USER: return new UserDAOImpl();
            case SERVICE: return new ServiceDAOImpl();
            case FACILITY: return new FacilityDAOImpl();
            case FLOOR: return new FloorDAOImpl();
            case JOB_ROLE: return new JobRoleDAOImpl();
            case PACKAGE: return new PackageDAOImpl();
            case PAYMENT: return new PaymentDAOImpl();
            case PAYMENT_TYPE: return new PaymentTypeDAOImpl();
            case PRICE_FLUC: return new PriceFlucDAOImpl();
            case QUERY: return new QueryDAOImpl();
            case RESERVATION: return new ReservationDAOImpl();
            case RESERVATION_ROOM: return new ReservationRoomDAOImpl();
            case RESERVATION_SERVICE: return new ReservationServiceDAOImpl();
            case ROOM_TYPE: return new RoomTypeDAOImpl();
            case ROOM_FACILITY: return new RoomFacilityDAOImpl();
            default: return null;
        }
    }
}
