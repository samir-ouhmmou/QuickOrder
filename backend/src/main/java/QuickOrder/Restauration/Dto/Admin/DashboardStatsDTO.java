package QuickOrder.Restauration.Dto.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {

    private long totalCommandes;
    private long commandesEnAttente;
    private long totalClients;
    private long totalPlats;
    private BigDecimal chiffreAffaires;
}
