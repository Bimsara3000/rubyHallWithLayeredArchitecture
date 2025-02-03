package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room {
    private String roomId;
    private String roomTypeId;
    private String floorId;
    private String state;
}
