package bohdan.abramovych.expandapis.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JwtResponseDTO {
    private String accessToken;

}
