package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Services.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
@AllArgsConstructor
public class CategorController {
    private CategorieService CategorieService;
    @GetMapping("/")
    public List<CategoryDto> getCategory(){
        List<CategoryDto> categories=new ArrayList<>();
        categories= CategorieService.getCategories();
        return categories;
    }
}
