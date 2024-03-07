package peaksoft.dto.request;

import java.util.List;

/**
 * @author Mukhammed Asantegin
 */
public record AddColourRequest(
        List<String> colours
) {
}
