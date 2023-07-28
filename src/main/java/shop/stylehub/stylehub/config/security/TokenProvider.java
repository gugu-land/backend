package shop.stylehub.stylehub.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;
import shop.stylehub.stylehub.user.entity.User;
import shop.stylehub.stylehub.user.entity.UserRole;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TokenProvider {

    // 서명에 사용할 값
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String createToken(User userEntity){

        // 토큰 만료시간 (6시간)
        Date expiryToken = Date.from(
                Instant.now().plus(6, ChronoUnit.HOURS)
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userEntity.getUserEmail());
        claims.put("role", userEntity.getUserRole());

        return Jwts.builder()
                // token header
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.ES512
                )
                // token payload
                .setIssuer("StyleHub")
                .setIssuedAt(new Date())
                .setExpiration(expiryToken)
                .setSubject(String.valueOf(userEntity.getUserId()))
                .setClaims(claims)
                .compact();
    }

    public TokenUserInfo validateAndGetTokenUserInfo(String token) {

        log.info("validateAndGetTokenUserInfo / token: {}", token);

        Claims claims = Jwts.parserBuilder()
                // 토큰 발급자의 발급 당시 서명을 넣어줌
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                // 서명 위조 검사 - 클라이언트 토큰의 서명과 서버 발급 당시 서명을 비교
                // 위조 X 경우 : payload(Claims) 를 리턴
                // 위조 O 경우 : 예외 발생
                .build()
                .parseClaimsJws(token)
                .getBody();

        return TokenUserInfo.builder()
                .userId(claims.getSubject())
                .userEmail(claims.get("userEmail", String.class))
                .userRole(UserRole.valueOf(
                                claims.get("userRole", String.class)
                        )
                )
                .build();
    }

}
