package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String userId;
    private String jobRoleId;
    private String name;
    private String email;
    private String password;
}
