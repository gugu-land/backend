package shop.stylehub.stylehub.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.stylehub.stylehub.config.security.TokenProvider;
import shop.stylehub.stylehub.user.dto.UserLoginRequestDTO;
import shop.stylehub.stylehub.user.entity.User;
import shop.stylehub.stylehub.user.repository.UserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final TokenProvider tokenProvider;


    public void authenticate(final UserLoginRequestDTO dto) {

        User user = userRepository.findByUserEmail(dto.getUserEmail())
                .orElseThrow(
                        () -> new RuntimeException("가입된 회원이 아닙니다!")
                );

        String rawPassword = dto.getUserPassword();
        String encodedPassword = user.getUserPassword();

        if (!encoder.matches(rawPassword, encodedPassword)){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
        // 토큰 발급
        tokenProvider.createToken(user);

    }
}
