package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceFlucDTO {
    private String priceFlucId;
    private String description;
    private String sDate;
    private String eDate;
    private int percentage;
}
