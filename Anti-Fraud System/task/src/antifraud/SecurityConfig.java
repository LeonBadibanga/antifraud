package antifraud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() // CSRF uitschakelen voor testen
                .httpBasic() // Gebruik HTTP Basic Authentication
                .and()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/auth/user/**").permitAll() //
                        .requestMatchers(HttpMethod.DELETE, "/api/auth/user/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.GET, "/api/auth/list").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/antifraud/transaction/**").hasRole("MERCHANT")
                        .requestMatchers(HttpMethod.PUT, "/api/auth/access/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/auth/role/**").hasRole("ADMINISTRATOR")
                        .requestMatchers("/actuator/shutdown/**").permitAll()
                        .requestMatchers("/error").permitAll()// Toegestaan zonder authenticatie
                        .anyRequest().permitAll() // Alle andere verzoeken vereisen authenticatie
                )
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Behandel authentificatiefouten
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Geen sessiebeheer
                .and()
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/* De beveiliging (SecurityConfig):
Dit deel zorgt ervoor dat niet zomaar iedereen overal in de applicatie kan komen.
Voor sommige onderdelen, zoals het aanmaken van nieuwe gebruikers,
hoeft de gebruiker niet ingelogd te zijn. Maar voor de rest moet je wel inloggen (jezelf authenticeren).
 */