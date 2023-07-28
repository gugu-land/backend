package shop.stylehub.stylehub.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.stylehub.stylehub.user.dto.request.UserLoginRequestDTO;
import shop.stylehub.stylehub.user.dto.request.UserSignUpRequestDTO;
import shop.stylehub.stylehub.user.dto.response.LoginUserResponseDTO;
import shop.stylehub.stylehub.user.service.UserLoginService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/stylehub/auth")
public class UserController {

    private final UserLoginService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Validated @RequestBody UserLoginRequestDTO dto
    ){
        try {
            log.info("requestDTO : {}", dto);
            userService.authenticate(dto);
            return ResponseEntity.status(200).body("로그인 성공!");

        }catch (RuntimeException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Validated @RequestBody UserSignUpRequestDTO dto
    ){
        log.info("signup : {}", dto);
        try {
            LoginUserResponseDTO responseDTO = userService.signUp(dto);
            log.info("responseDTO : {}", responseDTO);
            return ResponseEntity.ok(responseDTO);
        }catch (RuntimeException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
