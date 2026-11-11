package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Services.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategorController {
    private CategorieService CategorieService;
    @GetMapping
    public List<CategoryDto> getCategory(){
        return CategorieService.getCategories();
    }
}
