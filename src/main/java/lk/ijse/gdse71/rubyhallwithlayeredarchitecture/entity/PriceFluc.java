package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceFluc {
    private String priceFlucId;
    private String description;
    private String sDate;
    private String eDate;
    private int percentage;
}
