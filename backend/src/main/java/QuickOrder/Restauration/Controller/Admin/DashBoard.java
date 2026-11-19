package QuickOrder.Restauration.Controller.Admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoard {
    @GetMapping("/profile")
    public  getProfile(){

    }
}
