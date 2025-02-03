package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO {
    private String  PaymentId;
    private String  reservationId;
    private String  paymentTypeId;
    private String  date;
    private double  amount;
}
