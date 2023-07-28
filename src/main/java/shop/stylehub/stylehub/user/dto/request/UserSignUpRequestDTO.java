package shop.stylehub.stylehub.user.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import shop.stylehub.stylehub.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userEmail")
@Builder
public class UserSignUpRequestDTO {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String userPassword;

    @NotBlank
    private String userName;

    @NotBlank
    private String userNickName;

    private String userProfile;

    public User toEntity(MultipartFile multipartFile){
        return User.builder()
                .userEmail(this.userEmail)
                .userPassword(this.userPassword)
                .userName(this.userName)
                .userNickName(this.userNickName)
                .userProfile(String.valueOf(multipartFile))
                .build();
    }

}
