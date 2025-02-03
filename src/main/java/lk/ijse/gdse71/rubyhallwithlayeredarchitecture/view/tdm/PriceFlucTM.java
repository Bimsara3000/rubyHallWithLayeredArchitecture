package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceFlucTM {
    private String priceFlucId;
    private String description;
    private String sDate;
    private String eDate;
    private int percentage;
}
