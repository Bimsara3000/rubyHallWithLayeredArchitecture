package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationTM {
    private String reservationId;
    private String guestName;
    private String packageName;
    private String services;
    private String roomIds;
    private int guestCount;
    private String date;
    private String resDate;
    private String description;
}
