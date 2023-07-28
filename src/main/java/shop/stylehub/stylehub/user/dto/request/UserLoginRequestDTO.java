package shop.stylehub.stylehub.user.dto.request;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userEmail")
@Builder
public class UserLoginRequestDTO {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String userPassword;
}
