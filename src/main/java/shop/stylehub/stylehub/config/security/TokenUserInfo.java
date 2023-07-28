package shop.stylehub.stylehub.config.security;

import lombok.*;
import shop.stylehub.stylehub.user.entity.UserRole;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUserInfo {

    private String userId;
    private String userEmail;
    private UserRole userRole;
}