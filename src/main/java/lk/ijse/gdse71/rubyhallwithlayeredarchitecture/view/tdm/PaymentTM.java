package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentTM {
    private String  PaymentId;
    private String  guestName;
    private String  paymentType;
    private String  date;
    private double  amount;
}
