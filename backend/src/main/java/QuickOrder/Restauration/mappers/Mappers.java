package QuickOrder.Restauration.mappers;

import QuickOrder.Restauration.Dto.CategoryDto;
import QuickOrder.Restauration.Dto.PlatDto;
import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Model.Plat;
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

    public PlatDto fromPlat(Plat plat) {
        return new PlatDto(
                plat.getId(),
                plat.getNom(),
                plat.getDescription(),
                plat.getPrix(),
                plat.getDisponible(),
                plat.getCategorie() != null ? plat.getCategorie().getId() : null,
                plat.getImageUrl()
        );
    }

    public Plat fromPlatDto(PlatDto platDto) {
        Plat plat = new Plat();
        plat.setId(platDto.id());
        plat.setNom(platDto.nom());
        plat.setDescription(platDto.description());
        plat.setPrix(platDto.prix());
        plat.setDisponible(platDto.disponible());
        plat.setImageUrl(platDto.imageUrl());
        return plat;
    }
}
