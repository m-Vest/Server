//package mvest.core.global.config;
//
//import lombok.RequiredArgsConstructor;
//import mvest.core.auth.filter.JwtAuthenticationFilter;
//import mvest.core.auth.filter.JwtExceptionFilter;
//import mvest.core.auth.handler.CustomAccessDeniedHandler;
//import mvest.core.auth.handler.CustomJwtAuthenticationEntryPoint;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final JwtExceptionFilter jwtExceptionFilter;
//    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//
//    private static final String[] AUTH_WHITE_LIST = {
//            // auth
//            "/api/v1/auth/signup",
//            "/api/v1/auth/login",
//            "/api/v1/auth/reissue",
//
//            // swagger
//            "/swagger-ui.html",
//            "/swagger-ui/**",
//            "/v3/api-docs",
//            "/v3/api-docs/**"
//    };
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .requestCache(RequestCacheConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .exceptionHandling(exception ->
//                {
//                    exception.authenticationEntryPoint(customJwtAuthenticationEntryPoint);
//                    exception.accessDeniedHandler(customAccessDeniedHandler);
//                });
//
//        http
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(AUTH_WHITE_LIST).permitAll();
//                    auth.anyRequest().authenticated();
//                })
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
