package et.com.gebeya.user_service.dto.responseDto;

import et.com.gebeya.user_service.dto.requestDto.VendorDto;
import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VendorProductUpdateResponseDto {
    private String nameOfProduct;
    private int vendorQuantity;
    private Double vendorProductPrice;
    @ManyToOne
    private @Pattern(regexp = "^\\+251\\d{9}$") String phoneNumber;
    private Status status;
    private VendorDto vendor;

    public @Pattern(regexp = "^\\+251\\d{9}$") String setPhoneNumber(@Pattern(regexp = "^\\+251\\d{9}$") String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return phoneNumber;
    }
}
