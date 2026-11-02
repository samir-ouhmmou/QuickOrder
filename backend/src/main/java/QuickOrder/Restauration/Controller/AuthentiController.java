package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.LoginResponseDto;
import QuickOrder.Restauration.Dto.LoginrequestDto;
import QuickOrder.Restauration.Services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthentiController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginrequestDto loginrequestDto) {
        return authService.login(loginrequestDto);
    }
}