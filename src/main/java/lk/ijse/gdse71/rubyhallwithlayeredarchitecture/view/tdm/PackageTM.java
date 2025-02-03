package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageTM {
    private String packageId;
    private String name;
    private String description;
    private int duration;
    private double price;
    private String validity;
}
