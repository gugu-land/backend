package shop.stylehub.stylehub.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.stylehub.stylehub.user.dto.UserLoginRequestDTO;
import shop.stylehub.stylehub.user.service.UserLoginService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/stylehub/auth")
public class UserLoginController {

    private final UserLoginService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(
            @Validated @RequestBody UserLoginRequestDTO dto
    ){

        userService.authenticate(dto);

        return ResponseEntity.ok("");
    }



}
