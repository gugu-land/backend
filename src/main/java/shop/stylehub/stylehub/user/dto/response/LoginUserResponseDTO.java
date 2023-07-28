package shop.stylehub.stylehub.user.dto.response;

import lombok.*;
import shop.stylehub.stylehub.user.entity.User;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginUserResponseDTO {

    private UUID userId;
    private String userName;
    private String userNickName;
    private String userProfile;


    public LoginUserResponseDTO(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userNickName = user.getUserNickName();
        this.userProfile = user.getUserProfile();
    }
}
