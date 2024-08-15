package antifraud;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;



@RestController

//Spring controller. Het behandelt HTTP-verzoeken en geeft HTTP-reacties terug.
public class AntiFraudController {
    // Deze methode wordt aangeroepen wanneer een POST-verzoek wordt verzonden
    // naar de URL '/api/antifraud/transaction'.
    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity<Map<String, String>> checkTransaction(@RequestBody TransactionRequest request) {
        // Deze methode ontvangt een TransactionRequest-object als JSON via de body van het HTTP-verzoek.
        //Controle null of groter dan 0, geef een foutmelding als het niet zo is.
        //Hier wordt een HTTP 400 (Bad Request) teruggestuurd met een foutmelding.
        if (request.getAmount() == null || request.getAmount() <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Amount must be greater than 0"));
        }

        //string result bevat uitkomst fraudecheck
        String result;
        // Als het bedrag kleiner of gelijk is aan 200, is de transactie toegestaan.
        if (request.getAmount() <= 200) {
            result = "ALLOWED";
            // Als het bedrag tussen 201 en 1500 ligt, moet de transactie handmatig worden verwerkt.
        } else if (request.getAmount() <= 1500) {
            result = "MANUAL_PROCESSING";
            // Als het bedrag groter is dan 1500, wordt de transactie verboden.
        } else {
            result = "PROHIBITED";
        }
        // Hier wordt een HTTP 200 (OK) reactie teruggestuurd met het resultaat van de fraudecontrole.
        return ResponseEntity.ok().body(Map.of("result", result));
    }
}

/* De AntiFraudController-klasse behandelt verzoeken om
transacties te controleren op mogelijke fraude op basis van het
bedrag van de transactie.
Afhankelijk van het bedrag wordt de transactie toegestaan,
handmatig te controleren, of verboden.
Het resultaat van deze controle wordt dan
teruggestuurd naar de client die het verzoek deed.
*/