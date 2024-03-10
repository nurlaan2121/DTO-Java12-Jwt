package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.SignResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserRes;
import peaksoft.service.UserService;

import java.util.List;

/**
 * @author Mukhammed Asantegin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAPI {
    private final UserService userService;
    @GetMapping("/getAllClients")
    public List<UserRes> getAllClients(){
        System.out.println(userService.getAllClients().size());
        return userService.getAllClients();
    }
}
