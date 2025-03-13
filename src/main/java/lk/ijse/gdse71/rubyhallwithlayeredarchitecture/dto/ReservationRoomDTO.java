package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationRoomDTO {
    private String reservationId;
    private String roomId;
    private String startDate;
    private String endDate;
}
