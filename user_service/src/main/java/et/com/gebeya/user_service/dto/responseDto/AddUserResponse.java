package et.com.gebeya.user_service.dto.responseDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddUserResponse {
    private String token;
}