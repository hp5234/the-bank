package com.jeon.bank.config;

import com.jeon.bank.domain.user.UserEnum;
import com.jeon.bank.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");

        return new BCryptPasswordEncoder();
    }

    // TODO : JWT 필터 적용

    // JWT 를 사용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.debug("디버그 : filterChain 빈 등록됨");

        http    // enable 시 postman 동작 안함
                .csrf(csrf -> csrf.disable());
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http
                .headers((headers) ->
                        headers
                                // .contentTypeOptions(withDefaults())
                                // .xssProtection(withDefaults())
                                // .cacheControl(withDefaults())
                                // .httpStrictTransportSecurity(withDefaults())
                                .frameOptions(withDefaults()).disable()
                );
        http    // jsession Id 를 서버에서 관리하지 않겠다는 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http    // 앱을 통해 요청하겠다는 설정
                .formLogin(formLogin -> formLogin.disable());
        http    // httpBasic(브라우저가 팝업창을 통해 인증하는 방식) 사용 안함
                .httpBasic(httpBasic -> httpBasic.disable());

        http    // Exception 가로채기
                // Spring Security 가 인증, 권한 관련 에러를 감지해 공통 예외처리로 보내는 필터를 비활성화함
                .exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
                    // 공통 에러 Dto 적용
                    CustomResponseUtil.unAuthentication(response, "로그인을 진행해주세요.");
                }));

        http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/api/s/**").authenticated()
                                .requestMatchers("/api/admin/**").hasRole(UserEnum.ADMIN.name())
                                .anyRequest().permitAll()
                );

        // TODO : JWT 세팅

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        log.debug("디버그 : CorsConfigurationSource cors 설정이 SecurityFilterChain 에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

