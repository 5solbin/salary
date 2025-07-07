package solbin.project.salary.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import solbin.project.salary.util.CustomResponseUtil;

// check : JWT 관련 설정
@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BcryptPasswordEncoder 등록됨");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : SecurityFilterChain 등록됨");

        // iframe 허용을 안하기 위한 설정 (다른 페이지가 삽입되지 않도록 한다.)
        http.headers(h ->
                h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        // postman 사용을 위한 설정
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c ->  c.configurationSource(corsConfigurationSource()));

        // JsessionId는 서버에서 관리하지 않는다.
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 리엑트 혹은 앱으로 요청
        http.formLogin(AbstractHttpConfigurer::disable);
        // 팝업창 인증 해제
        http.httpBasic(AbstractHttpConfigurer::disable);


        // 인증 실패
        http.exceptionHandling(h -> h.authenticationEntryPoint((request, response, oauthException) ->
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED))
        );

        // 권한 실패
        http.exceptionHandling(h -> h.accessDeniedHandler((request, response, authException) ->
                CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN)));

        http.authorizeHttpRequests(
                a-> a
                        .requestMatchers("/api/s/**").authenticated()
                        .requestMatchers("api/admin/**").hasRole("ADMIN") // check : 필요에 따라서 수정
                        .anyRequest().permitAll()
        );

        // check :

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        log.debug("디버그 :  CorsConfigurationSource 등록됨");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
