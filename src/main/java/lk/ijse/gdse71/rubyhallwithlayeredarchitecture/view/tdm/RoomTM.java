package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomTM {
    private String roomId;
    private String roomType;
    private String floor;
    private String state;
    private String facilities;
}
