package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDTO {
    private String roomId;
    private String roomTypeId;
    private String floorId;
    private String state;
}
