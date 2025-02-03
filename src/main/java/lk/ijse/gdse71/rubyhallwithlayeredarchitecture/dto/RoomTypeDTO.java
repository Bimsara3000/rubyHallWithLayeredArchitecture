package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomTypeDTO {
    private String roomTypeId;
    private String description;
    private double price;
}
