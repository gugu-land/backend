package shop.stylehub.stylehub.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.stylehub.stylehub.user.dto.request.UserSignUpRequestDTO;

@SpringBootTest
//@Transactional
class UserLoginServiceTest {

    @Autowired
    UserLoginService userLoginService;

    @Test
    void signUp() {
        UserSignUpRequestDTO requestDTO = UserSignUpRequestDTO.builder()
                .userEmail("jkh991116@naver.com")
                .userPassword("1234")
                .userName("조경훈")
                .userNickName("oslob")
                .build();

        userLoginService.signUp(requestDTO);
    }
}