package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Services.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategorController {
    private final CategorieService categorieService;
    @GetMapping
    public List<CategoryDto> getCategory(){
        return categorieService.getCategories();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.createCategory(category));
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto category) {
        return categorieService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categorieService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
