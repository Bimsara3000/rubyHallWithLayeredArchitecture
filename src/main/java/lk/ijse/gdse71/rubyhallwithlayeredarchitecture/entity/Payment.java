package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private String  PaymentId;
    private String  reservationId;
    private String  paymentTypeId;
    private String  date;
    private double  amount;
}
