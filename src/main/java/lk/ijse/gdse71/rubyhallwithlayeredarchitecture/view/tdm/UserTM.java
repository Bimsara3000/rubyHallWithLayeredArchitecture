package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTM {
    private String userId;
    private String jobRole;
    private String name;
    private String email;
    private String password;
}
