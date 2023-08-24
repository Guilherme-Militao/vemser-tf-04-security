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
                        .antMatchers("/auth/criar-usuario").permitAll()
                        .antMatchers(HttpMethod.GET, "/usuario/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/usuario/usuario-receita").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/usuario/usuario-investimento").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/usuario/usuario-despesa").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/usuario/usuario-dados").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/usuario/recuperar-usuario-logado").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/usuario/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/usuario/{idUsuario:[0-9]+}/login").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.DELETE, "/usuario/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/receita/{idReceita:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.POST, "/receita/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/receita/{idReceita:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.DELETE, "/receita/{idReceita:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/investimentos/{idInvestimento:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.POST, "/investimentos/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/investimentos/{idInvestimento:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.DELETE, "/investimentos/{idInvestimento:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/despesa/{idDespesa:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.POST, "/despesa/{idUsuario:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/despesa/{idDespesa:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.DELETE, "/despesa/{idDespesa:[0-9]+}").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers("/usuario/**").hasRole("ADMIN")
                        .antMatchers("/investimentos/**").hasRole("ADMIN")
                        .antMatchers("/receita/**").hasRole("ADMIN")
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
