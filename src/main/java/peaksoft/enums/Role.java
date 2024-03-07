package peaksoft.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Mukhammed Asantegin
 */
public enum Role implements GrantedAuthority
 {
    ADMIN,
    CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
