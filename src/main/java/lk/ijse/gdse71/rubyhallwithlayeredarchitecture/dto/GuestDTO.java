package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestDTO {
    private String guestId;
    private String name;
    private String email;
    private int phoneNo;
}
