package antifraud;

public class TransactionRequest {

    // bedrag vd transactie
    private Long amount;

    //'get' het bedrag vd transactie
    public Long getAmount() {
        return amount;
    }

    //'set' het bedrag vd transactie
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

/*De TransactionRequest klasse wordt gebruikt om
informatie over een transactie vast te leggen en door te
geven aan je applicatie.
 */