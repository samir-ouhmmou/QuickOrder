package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.TableRestaurantDto;
import QuickOrder.Restauration.Model.Etat;
import QuickOrder.Restauration.Repository.TableRestaurantRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableController {
    private final TableRestaurantRepo tableRestaurantRepo;

    @GetMapping("/disponibles")
    public List<TableRestaurantDto> getTablesDisponibles() {
        return tableRestaurantRepo.findByEtat(Etat.LIBRE).stream()
                .map(table -> new TableRestaurantDto(table.getId(), table.getNumero(), table.getCapacite()))
                .toList();
    }
}
