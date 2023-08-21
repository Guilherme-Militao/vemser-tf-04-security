package br.com.dbc.vemser.walletlife.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors()
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .authorizeHttpRequests((authz) ->  authz
                        .antMatchers("/auth", "/").permitAll()
                        .antMatchers(HttpMethod.GET, "/usuario/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/receita/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/investimento/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/despesa/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/usuario/{idUsuario:[0-9]+}/login").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.DELETE, "/usuario/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers("/usuario/**").hasRole("ADMIN")
                        .antMatchers("/receita/**").hasRole("ADMIN")
                        .antMatchers("/investimento/**").hasRole("ADMIN")
                        .antMatchers("/despesa/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new StandardPasswordEncoder();
    }
}
