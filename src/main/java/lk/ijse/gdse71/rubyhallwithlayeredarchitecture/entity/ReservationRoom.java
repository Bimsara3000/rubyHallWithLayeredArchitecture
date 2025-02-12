package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationRoom {
    private String reservationId;
    private String roomId;
    private String startDate;
    private String endDate;
}
