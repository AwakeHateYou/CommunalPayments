import javax.persistence.*;

/**
 * Объект, мооделирующий счет.
 *
 * @author Терентьев Евгений
 */
@Entity
@Table(name = "Payment", schema = "payments")
public class PaymentEntity {
    private int id;
    /**
     * ФИО человека.
     */
    private String fio;
    private double price;
    private double priceDone;
    private String payType;
    /**
     * Геттер для id
     * @return id объекта
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }
    /**
     * Сеттер для id
     * @param id id объекта
     */
    public void setId(int id) {
        this.id = id;
    }

}
