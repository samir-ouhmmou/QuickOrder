package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.PlatDto;
import QuickOrder.Restauration.Repository.PLatRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plats")
@CrossOrigin("*")
@AllArgsConstructor
public class PlatController {
    private PLatRepo platRepo;

    @GetMapping
    public List<PlatDto> getPlatsByCategory(@RequestParam Long categoryId) {
        return platRepo.findByCategorie_Id(categoryId).stream()
                .map(plat -> new PlatDto(
                        plat.getId(),
                        plat.getNom(),
                        plat.getDescription(),
                        plat.getPrix(),
                        plat.getDisponible()
                ))
                .toList();
    }
}
