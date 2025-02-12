package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationService {
    private String serviceId;
    private String reservationId;
    private int duration;
}
