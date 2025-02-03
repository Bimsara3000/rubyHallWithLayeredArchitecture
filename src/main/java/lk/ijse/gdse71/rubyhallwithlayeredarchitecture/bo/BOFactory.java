package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl.AddGuestBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getInstance() {
        return boFactory==null? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        ADD_GUEST
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case ADD_GUEST: return new AddGuestBOImpl();
            default: return null;
        }
    }
}
