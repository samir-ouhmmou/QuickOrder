package QuickOrder.Restauration.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
private Long id;
private String nom;
private String description;
private Boolean active;
private Boolean disponibilté;
private Long nombresPlats;
}
