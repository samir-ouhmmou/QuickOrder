package QuickOrder.Restauration.Controller;

import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Services.dashBoardImpli;
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
    private dashBoardImpli dashBoardImpli;
    @GetMapping("/category")
    public List<Categorie> getCategory(){
        List<Categorie> categories=new ArrayList<>();
        categories=dashBoardImpli.getCategories();
        return categories;
    }
}
