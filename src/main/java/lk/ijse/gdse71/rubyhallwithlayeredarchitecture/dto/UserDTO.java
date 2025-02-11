package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String userId;
    private String jobRoleId;
    private String name;
    private String email;
    private String password;

    public UserDTO(String userId, String jobRoleId, String email, String password) {
        this.userId = userId;
        this.jobRoleId = jobRoleId;
        this.email = email;
        this.password = password;
    }
}
