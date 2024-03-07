package peaksoft.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author Mukhammed Asantegin
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleResponse {
    private HttpStatus httpStatus;
    private String message;

}
