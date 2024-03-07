package peaksoft.dto.response;

import java.math.BigDecimal;

/**
 * @author Mukhammed Asantegin
 */
public record ProductResponse(
        String image,
        String name,
        BigDecimal price) {
}
