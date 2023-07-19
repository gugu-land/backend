package shop.stylehub.stylehub.user.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@Builder
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "userId", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "user_email", unique = true, nullable = false, length = 200)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 500)
    private String userPassword;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name = "user_nickname", unique = true, nullable = false, length = 20)
    private String userNickname;

    @CreationTimestamp
    @Column(name = "user_regdate", nullable = false)
    private LocalDateTime userRegdate;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'COMMON'")
    private UserRole userRole;

    @Column(name = "user_profile", length = 800)
    private String userProfile;
}
