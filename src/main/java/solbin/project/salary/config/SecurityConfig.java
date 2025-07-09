package solbin.project.salary.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import solbin.project.salary.config.jwt.JwtAuthenticationFilter;
import solbin.project.salary.config.jwt.JwtAuthorizationFilter;
import solbin.project.salary.domain.user.UserEnum;
import solbin.project.salary.util.CustomResponseUtil;

/**
 * Security 설정
 * JWT 인증/인가
 * CSRF, CORS 설정
 * 인증, 권한 실패시 커스텀 응답 처리
 * BcryptPasswordEncoder 및 Authentication Bean 등록
 */

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // 비밀번호 암호화를 위한 빈 등록 메서드
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BcryptPasswordEncoder 등록됨");
        return new BCryptPasswordEncoder();
    }

    // 인증을 위한 빈 등록 메서드
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 시큐리티 설정을 위한 빈 등록 메서드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        log.debug("디버그 : SecurityFilterChain 등록됨");

        // iframe 허용을 안하기 위한 설정 (다른 페이지가 삽입되지 않도록 한다.)
        http.headers(h ->
                h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        // postman 사용을 위한 설정
        http.csrf(AbstractHttpConfigurer::disable);
        // cors 설정
        http.cors(c ->  c.configurationSource(corsConfigurationSource()));

        // JsessionId는 서버에서 관리하지 않는다.
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 리엑트 혹은 앱으로 요청
        http.formLogin(AbstractHttpConfigurer::disable);
        // 팝업창 인증 해제
        http.httpBasic(AbstractHttpConfigurer::disable);

        // JWT 설정
        http.addFilter(new JwtAuthenticationFilter(authenticationManager));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager));

        // 인증 실패
        http.exceptionHandling(h -> h.authenticationEntryPoint((request, response, oauthException) ->
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED))
        );

        // 권한 실패
        http.exceptionHandling(h -> h.accessDeniedHandler((request, response, authException) ->
                CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN)));

        // 접근 권한 설정
        http.authorizeHttpRequests(
                a -> a
                        .requestMatchers("/api/s/**").authenticated()
                        .requestMatchers("api/admin/**").hasRole(UserEnum.ADMIN + "")
                        .anyRequest().permitAll()
        );

        return http.build();
    }

    // Cors 설정을 위한 메서드 (다른 리소스 접근을 위한 메서드)
    public CorsConfigurationSource corsConfigurationSource() {
        log.debug("디버그 :  CorsConfigurationSource 등록됨");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (javascript 요청 허용)
        corsConfiguration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
        corsConfiguration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        corsConfiguration.addExposedHeader("Authorization"); // 헤더에 authorization 값을 노출 시켜서 JS에서 값을 가져올 수 있도록 함

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
