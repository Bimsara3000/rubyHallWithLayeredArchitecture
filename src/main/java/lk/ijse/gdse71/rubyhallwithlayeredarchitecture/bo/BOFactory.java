package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.GuestBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getInstance() {
        return boFactory==null? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        GUEST
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case GUEST: return new GuestBOImpl();
            default: return null;
        }
    }
}
