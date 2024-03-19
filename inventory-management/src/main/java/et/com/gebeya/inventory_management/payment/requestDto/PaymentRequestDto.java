package et.com.gebeya.inventory_management.payment.requestDto;

import et.com.gebeya.inventory_management.enums.PaymentStatus;
import et.com.gebeya.inventory_management.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequestDto {
    private Long updateRequestId;
    private String senderPhoneNumber;
    private PaymentStatus status;

}
