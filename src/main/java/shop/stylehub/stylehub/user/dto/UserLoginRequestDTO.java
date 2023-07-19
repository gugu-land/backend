package shop.stylehub.stylehub.user.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRequestDTO {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String userPassword;
}
