package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.UserRes;
import peaksoft.service.UserService;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAPI {
    private final UserService userService;
    @Secured("ADMIN")
    @GetMapping("/getAllClients")
    public List<UserRes> getAllClients(){
        System.out.println(userService.getAllClients().size());
        return userService.getAllClients();
    }
}
