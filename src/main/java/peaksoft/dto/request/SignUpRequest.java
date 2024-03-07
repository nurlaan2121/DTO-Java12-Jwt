package peaksoft.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mukhammed Asantegin
 */
@Getter @Setter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;

}
