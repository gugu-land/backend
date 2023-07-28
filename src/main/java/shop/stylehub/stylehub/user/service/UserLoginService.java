package shop.stylehub.stylehub.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.stylehub.stylehub.config.security.TokenProvider;
import shop.stylehub.stylehub.user.dto.request.UserLoginRequestDTO;
import shop.stylehub.stylehub.user.dto.request.UserSignUpRequestDTO;
import shop.stylehub.stylehub.user.dto.response.LoginUserResponseDTO;
import shop.stylehub.stylehub.user.entity.User;
import shop.stylehub.stylehub.user.repository.UserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final TokenProvider tokenProvider;


    public void authenticate(final UserLoginRequestDTO dto) throws RuntimeException{

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

    /**
     *
     * @param dto
     * dto에서는 validate로 유효성 검사
     * 이메일, 닉네임 중복검사
     * 비밀번호 암호화 후 저장
     */
    public LoginUserResponseDTO signUp(UserSignUpRequestDTO dto) throws RuntimeException{

    // 이메일, 닉네임 중복검사
        boolean emailDuplicate = checkEmailDuplicate(dto.getUserEmail());
        boolean nickNameDuplicate = checkNickNameDuplicate(dto.getUserNickName());

        if (emailDuplicate)
            throw new RuntimeException("이메일이 중복되었습니다.");

        if (nickNameDuplicate)
            throw new RuntimeException("닉네임이 중복되었습니다.");


    // 비밀번호 암호화
        String encodePassword = encoder.encode(dto.getUserPassword());

        dto.setUserPassword(encodePassword);
//        MultipartFile multipartFile = null;

        User user = dto.toEntity(null);
        log.info("user!!: {}",user);

        User save = userRepository.save(user);
        log.info("save@@: {}",save);

        return new LoginUserResponseDTO(save);

    }

    public boolean checkEmailDuplicate(String userEmail){
        return userRepository.existsByUserEmail(userEmail);
    }

    public boolean checkNickNameDuplicate(String userNickName){
        return userRepository.existsByUserNickName(userNickName);
    }


}
