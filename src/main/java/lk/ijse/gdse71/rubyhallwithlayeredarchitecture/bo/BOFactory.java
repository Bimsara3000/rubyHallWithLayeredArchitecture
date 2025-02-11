package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.*;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getInstance(BOType user) {
        return boFactory==null? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        GUEST,ROOM,USER,SERVICE,FACILITY,FLOOR,JOB_ROLE,PACKAGE,PAYMENT,PAYMENT_TYPE,PRICE_FLUC,QUERY,RESERVATION,RESERVATION_ROOM,RESERVATION_SERVICE,ROOM_TYPE
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case GUEST: return new GuestBOImpl();
            case ROOM: return new RoomBOImpl();
            case USER: return new UserBOImpl();
            case SERVICE: return new ServiceBOImpl();
            case FACILITY: new FacilityBOImpl();
            case FLOOR: new FloorBOImpl();
            case JOB_ROLE: new JobRoleBOImpl();
            case PACKAGE: new PackageBOImpl();
            case PAYMENT: new PaymentBOImpl();
            case PAYMENT_TYPE: new PaymentTypeBOImpl();
            case PRICE_FLUC: new PriceFlucBOImpl();
            case QUERY: new QueryBOImpl();
            case RESERVATION: new RoomBOImpl();
            case RESERVATION_ROOM: new ReservationRoomBOImpl();
            case RESERVATION_SERVICE: new ReservationServiceBOImpl();
            case ROOM_TYPE: new RoomTypeBOImpl();
            default: return null;
        }
    }
}
