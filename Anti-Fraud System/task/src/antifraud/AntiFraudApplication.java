package antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



// Dit is een annotatie die Spring Boot vertelt dat dit een hoofdapplicatieklasse is.
// Het configureert automatisch alles wat nodig is om de applicatie uit te voeren.

@SpringBootApplication
public class AntiFraudApplication {
    //main method word gestart
    public static void main(String[] args) {
        SpringApplication.run(AntiFraudApplication.class, args);
    }
}


/*

-application properties : spring.datasource.url=jdbc:h2:file:../service_db.

-dependency toevoegen

-config klasse maken

-RestAuthenticationEntryPoint klasse

-AuthenticationEntryPoint interface.

 */