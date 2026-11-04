package QuickOrder.Restauration.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginrequestDto {
 @NotBlank(message = "L'email est Oblegatoire")
 @Email(message = "format email valid")
 private String email;
 @NotBlank(message = "mot de pass oblegatoire")
 private String password;
}
