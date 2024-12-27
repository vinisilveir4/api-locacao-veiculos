package com.vinisilveir4.locacao_veiculos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(crs -> crs.disable())
                .csrf(csrf -> csrf.disable())
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.sendError(
//                                    HttpServletResponse.SC_UNAUTHORIZED,
//                                    ex.getMessage()
//                            );
//                        }
//                ))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/auth/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers( HttpMethod.POST,"/veiculo/novo").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/veiculo/remover").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/veiculo/atualizar").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/veiculo/todos").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/veiculo/disponiveis").hasAnyAuthority("ADMIN", "USUARIO", "VENDEDOR")
                        .requestMatchers(HttpMethod.POST, "/locacao/solicitar").hasAnyAuthority("USUARIO", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/locacao/visualizar").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/locacao/atualizar").hasAnyAuthority("ADMIN", "VENDEDOR")
                        .requestMatchers(HttpMethod.POST, "/usuario/adicionar-vendedor").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                )
                .build();
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
