package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Role;

/**
 * @author Mukhammed Asantegin
 */
@Builder
public record SignResponse(
        Long id,
        String token,
        Role role,
        String email
){
}
