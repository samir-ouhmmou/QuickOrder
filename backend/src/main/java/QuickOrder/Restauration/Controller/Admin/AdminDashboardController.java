package QuickOrder.Restauration.Controller.Admin;

import QuickOrder.Restauration.Dto.Admin.DashboardStatsDTO;
import QuickOrder.Restauration.Services.Admin.AdminDashboardServiceInterImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@AllArgsConstructor
public class AdminDashboardController {
    private final AdminDashboardServiceInterImpl adminDashboardServiceInter;
    @GetMapping("/stats")
    public DashboardStatsDTO getDashboardState(){
        return  adminDashboardServiceInter.getDashboardStats();
    }
}
