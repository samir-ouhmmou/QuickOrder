package QuickOrder.Restauration.Services.Admin;

import QuickOrder.Restauration.Dto.Admin.DashboardStatsDTO;
import QuickOrder.Restauration.Repository.ClientRepo;
import QuickOrder.Restauration.Repository.CommandeRepo;
import QuickOrder.Restauration.Repository.PLatRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AdminDashboardServiceInterImpl implements AdminDashboardServiceInter {
    private final CommandeRepo commandeRepo;
    private final ClientRepo clientRepo;
    private final PLatRepo pLatRepo;
    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalCommandes = commandeRepo.count();
        long totalClients = clientRepo.count();
        long totalPlats = pLatRepo.count();

        return new DashboardStatsDTO(totalCommandes,0,totalClients,totalPlats, BigDecimal.ZERO);
    }
}
