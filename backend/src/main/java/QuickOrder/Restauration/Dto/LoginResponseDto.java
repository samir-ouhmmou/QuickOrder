package QuickOrder.Restauration.Dto;

import QuickOrder.Restauration.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private String type;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;

}
