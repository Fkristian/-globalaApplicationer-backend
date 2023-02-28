package se.kth.iv1201.appserv.jobapp.core.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import se.kth.iv1201.appserv.jobapp.core.config.security.JwtAuthenticateFilter;

import java.util.Arrays;

import java.util.List;

/**
 * Class implemented as a security-mechanism to authenticate every request and check permission rules.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticateFilter jwtAuthenticateFilter;

    private final AuthenticationProvider authenticationProvider;

    /**
     * The security filter Bean that will match every request and check what permits and
     * authentications are allowed.
     *
     * @param http the http-request to be matched
     * @return the http-request
     * @throws Exception thrown when a general error or failure occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/demo-Controller").permitAll()
                .requestMatchers("/api/application/**").permitAll()
                .requestMatchers("/admin/**").hasRole("1")
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Bean containing CORS configurations to be added to the security filter chain.
     * @return the specified CORS configurations.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://frontendjobbapp.herokuapp.com/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("X-Requested-With","X-HTTP-Method-Override","Content-Type","Authorization","Accept","Access-Control-Allow-Credentials","Access-Control-Allow-Origin"));
        UrlBasedCorsConfigurationSource soruce = new UrlBasedCorsConfigurationSource();
        soruce.registerCorsConfiguration("/**", configuration);
        return soruce;
    }


}
