package com.dajungdagam.dg.config;

import com.dajungdagam.dg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final List<String> allowedUris = new ArrayList<>(Arrays.asList("/h2-console/**", "/log/**"));

    // h2-console 사용하기 위해 보안 허용
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(toH2Console()).disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(toH2Console()).permitAll())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }*/
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .headers(it -> it.frameOptions().sameOrigin())
//                .authorizeHttpRequests(
//                        it -> it.requestMatchers("/**").permitAll()
//                                .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }


    // JWT 토큰 사용 시 설정
    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .requestMatchers("/login/**").permitAll() // login은 전체 허용
                .requestMatchers("/login/logout/**").permitAll() // login은 전체 허용
                .requestMatchers("/h2-console/**").permitAll() // h2-console은 전체 허용
                .requestMatchers("/trade/**").permitAll() // h2-console은 전체 허용
                .requestMatchers(HttpMethod.POST, "/mypage/**").authenticated() // mypage는 인증하도록 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 사용시 사용
                .and()
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class) // jwtfiler에서 처리하도록
                .build();
    }
    
    // // login ignoring
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return web -> {
    //         web.ignoring()
    //                 .requestMatchers(
    //                         "/login/**"
    //                 );
    //     };
    // }
    
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration config = new CorsConfiguration();

    //     config.setAllowCredentials(true);
    //     config.setAllowedOrigins(List.of("https://localhost:3000"));
    //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    //     config.setAllowedHeaders(List.of("*"));
    //     config.setExposedHeaders(List.of("*"));

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);
    //     return source;
    // }
    
}
