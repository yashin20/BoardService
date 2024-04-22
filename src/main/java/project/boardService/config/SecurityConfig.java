package project.boardService.config;

/*

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // BCryptPasswordEncoder Bean 등록 - password 암호화
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // WebSecurityCustomizer Bean 등록 - 정적 resources 접근을 위함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스가 위치한 파일의 보안 처리를 무시 (누구든 접근 가능)
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    // authorizeHttpRequests - '/' , '/user/login' , '/user/new' 페이지를 로그인 없이 접근
    // formLogin - '/login' 페이지를 커스터마이징 하여 로그인 체크를 할때 해당 url 을 타도록 셋팅하고
    // 로그인 성공시 '/' 페이지로 이동하도록 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/user/login", "/user/new").permitAll()
                        .requestMatchers("/user/info").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll());

        return http.build();
    }
}
*/
