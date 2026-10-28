package QuickOrder.Restauration.Services;

import QuickOrder.Restauration.Model.Categorie;
import QuickOrder.Restauration.Repository.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
public class platserviceimpli implements platserviceinterface{
    private CategoryRepo categoryRepo;

}
