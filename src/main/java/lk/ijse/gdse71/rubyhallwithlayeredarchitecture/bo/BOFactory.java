package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getInstance() {
        return boFactory==null? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        GUEST,ROOM,USER,SERVICE,FACILITY,FLOOR,JOB_ROLE,PACKAGE,PAYMENT,PAYMENT_TYPE,PRICE_FLUC,QUERY,RESERVATION,RESERVATION_ROOM,RESERVATION_SERVICE,ROOM_TYPE,ROOM_FACILITY
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case GUEST: return new GuestBOImpl();
            case ROOM: return new RoomBOImpl();
            case USER: return new UserBOImpl();
            case SERVICE: return new ServiceBOImpl();
            case FACILITY: return new FacilityBOImpl();
            case FLOOR: return new FloorBOImpl();
            case JOB_ROLE: return new JobRoleBOImpl();
            case PACKAGE: return new PackageBOImpl();
            case PAYMENT: return new PaymentBOImpl();
            case PAYMENT_TYPE: return new PaymentTypeBOImpl();
            case PRICE_FLUC: return new PriceFlucBOImpl();
            case QUERY: return new QueryBOImpl();
            case RESERVATION: return new ReservationBOImpl();
            case RESERVATION_ROOM: return new ReservationRoomBOImpl();
            case RESERVATION_SERVICE: return new ReservationServiceBOImpl();
            case ROOM_TYPE: return new RoomTypeBOImpl();
            case ROOM_FACILITY: return new RoomFacilityBOImpl();
            default: return null;
        }
    }
}
