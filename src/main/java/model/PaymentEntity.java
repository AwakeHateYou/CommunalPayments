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

    /**
     * Геттер для ФИО.
     * @return ФИО
     */
    @Basic
    @Column(name = "fio")
    public String getFio(){ return fio; }

    /**
     * Сеттер ФИО
     * @param fio ФИО
     */
    public void setFio(String fio) {
        this.fio = fio;
    }

    /**
     * Геттер суммы платежа
     * @return сумма платежа
     */
    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public double getPriceDone() {
        return priceDone;
    }

    public String getPayType() {
        return payType;
    }



    public void setPrice(double price) {
        this.price = price;
    }

    public void setPriceDone(double priceDone) {
        this.priceDone = priceDone;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
