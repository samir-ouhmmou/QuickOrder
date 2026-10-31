package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Repository.CategoryRepo;
import QuickOrder.Restauration.mappers.Mappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategorieService implements dashBoardinterfase {
        private CategoryRepo categoryRepo;
        private Mappers mappers;
    @Override
    public List<CategoryDto> getCategories() {
        List<Categorie> categories=new ArrayList<>();
        categories=categoryRepo.findAll();

        return  categories.stream().map(mappers::fromCategory).toList();
    }
}
