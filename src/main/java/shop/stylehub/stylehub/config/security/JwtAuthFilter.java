package shop.stylehub.stylehub.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // 요청 정보 받아오기
            HttpServletResponse response, // 응답 정보 생성
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);

            log.info("JWT TOKEN FILTER IS RUNNING.... - token : {}", token);

            // 토큰 위조검사 및 인증 완료 처리
            if (token != null) {

                // 토큰 서명 위조 검사 & 토큰을 파싱해서 클레임 얻기
                TokenUserInfo userInfo
                        = tokenProvider.validateAndGetTokenUserInfo(token);

                System.out.println("userInfo = " + userInfo);

                log.info("doFilterInternal...1");

                // 인가 정보 리스트
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                authorityList.add(new SimpleGrantedAuthority(userInfo.getUserRole().toString()));

                log.info("doFilterInternal...2");

                // 인증 완료 처리 - 스프링 시큐리티에 인증정보를 전달해서 전역적으로 앱에서 인증 정보를 활용할 수 있게 설정
                AbstractAuthenticationToken auth
                        = new UsernamePasswordAuthenticationToken(
                        userInfo, // 컨트롤러에서 활용할 유저 정보
                        null, // 인증된 사용자의 비밀번호
                        authorityList // 인가 정보
                );

                log.info("doFilterInternal...3");

                // 인증 완료 처리 시 클라이언트의 요청정보 세팅
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                log.info("doFilterInternal...4");

                // 스프링 시큐리티 컨테이너에 인증정보 객체 등록
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("doFilterInternal...finish!");

            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        // 필터 체인에 내가 만든 필터 실행 명령
        filterChain.doFilter(request, response);

    }

    protected String parseBearerToken(
            HttpServletRequest request
    ) {

        log.info("pure token:{}", request.getHeader("Authorization"));

        // 요청 헤더에서 토큰 가져오기 - "Authorization" : "Bearer {token}"
        String bearerToken = request.getHeader("Authorization");

        log.info("bearerToken:{}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}