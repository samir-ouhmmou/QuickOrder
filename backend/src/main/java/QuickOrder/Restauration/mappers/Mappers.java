package QuickOrder.Restauration.mappers;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Model.Categorie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class Mappers {
    public CategoryDto fromCategory(Categorie categorie){
        CategoryDto categoryDto=new CategoryDto();
        BeanUtils.copyProperties(categorie,categoryDto);
        if(categorie.getPlats()!=null){
            categoryDto.setNombresPlats(categorie.getPlats().stream().count());
        }else {
            categoryDto.setNombresPlats(0L);
        }
        return categoryDto;
    }
    public Categorie fromCategoryDto(CategoryDto categoryDto){
        Categorie categorie=new Categorie();
        BeanUtils.copyProperties(categoryDto,categorie);
        return categorie;
    }
}
