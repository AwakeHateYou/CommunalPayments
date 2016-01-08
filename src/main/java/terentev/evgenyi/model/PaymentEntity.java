package terentev.evgenyi.model;

import javax.persistence.*;

/**
 * Объект, мооделирующий счет.
 *
 * @author Терентьев Евгений
 */
@Entity
@Table(name = "Payment", schema = "Payments")
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

    /**
     * Сеттер суммы платежа.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Геттер внесенной суммы
     * @return внесенная сумма
     */
    @Basic
    @Column(name = "price_done")
    public double getPriceDone() {
        return priceDone;
    }

    /**
     * Сеттер внесенной суммы
     * @param priceDone внесенная сумма
     */
    public void setPriceDone(double priceDone) {
        this.priceDone = priceDone;
    }

    /**
     * Геттер вида платежа
     * @return вид платежа
     */
    @Basic
    @Column(name = "default_pay_type")
    public String getPayType() {
        return payType;
    }

    /**
     * Сеттер вида платежа
     * @param payType вид платежа
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

}
