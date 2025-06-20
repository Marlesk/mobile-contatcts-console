package gr.aueb.cf.mobilecontacts.model;

/*
 * το id είναι μοναδικό αναγνωριστικό που προσδιορίζει και
 * διαφοροποιεί μια οντότητα από άλλες στο σύστημα
 */
public abstract class AbstractEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
