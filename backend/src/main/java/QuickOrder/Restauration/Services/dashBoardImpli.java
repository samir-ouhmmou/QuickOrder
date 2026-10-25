package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Repository.CategoryRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class dashBoardImpli implements dashBoardinterfase {
        private CategoryRepo categoryRepo;

    @Override
    public List<Categorie> getCategories() {
        List<Categorie> categories=new ArrayList<>();
        categories=categoryRepo.findAll();
        return  categories;
    }
}
