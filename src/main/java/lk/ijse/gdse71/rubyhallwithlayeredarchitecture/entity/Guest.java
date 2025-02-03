package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guest {
    private String guestId;
    private String name;
    private String email;
    private int phoneNo;
}
