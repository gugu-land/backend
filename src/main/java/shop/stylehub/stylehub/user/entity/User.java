package shop.stylehub.stylehub.user.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "userId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "userId", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "user_email", unique = true, nullable = false, length = 200)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 500)
    private String userPassword;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name = "user_nickname", unique = true, nullable = false, length = 20)
    private String userNickName;

    @CreationTimestamp
    @Column(name = "user_regdate", nullable = false)
    private LocalDateTime userRegdate;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'COMMON'")
    private UserRole userRole;

    @Column(name = "user_profile", length = 800)
    private String userProfile;

    @PrePersist
    public void createUserId(){
        // sequential uuid 생성
//        UUID uuid = Generators.timeBasedGenerator().generate();
//        String[] uuidArr = uuid.toString().split("-");
//        String uuidStr =
//                uuidArr[2]+uuidArr[1]+uuidArr[0]+uuidArr[3]+uuidArr[4];
//        StringBuffer stringBuffer = new StringBuffer(uuidStr);
//        stringBuffer.insert(8, "-");
//        stringBuffer.insert(13, "-");
//        stringBuffer.insert(18, "-");
//        stringBuffer.insert(23, "-");
//        uuid = UUID.fromString(stringBuffer.toString());
//        this.userId = uuid;
        this.userId = UuidCreator.getTimeOrdered();
    }
}
