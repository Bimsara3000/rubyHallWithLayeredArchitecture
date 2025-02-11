package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.GuestBOImpl;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.RoomBOImpl;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getInstance(BOType user) {
        return boFactory==null? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        GUEST,ROOM,USER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case GUEST: return new GuestBOImpl();
            case ROOM: return new RoomBOImpl();
            case USER: return new UserBOImpl();
            default: return null;
        }
    }
}
