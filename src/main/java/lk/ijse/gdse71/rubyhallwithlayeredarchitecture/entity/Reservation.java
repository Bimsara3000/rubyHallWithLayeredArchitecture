package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    private String reservationId;
    private String userId;
    private String guestId;
    private String packageId;
    private int guestCount;
    private String date;
    private String description;
}
