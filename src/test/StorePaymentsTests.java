import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Тестирование класса StorePayments.
 * @author Терентьев Евгений
 */
public class StorePaymentsTests {
    /**
     * Добавление объектов в базу
     * @throws Exception исключение
     */
    @Before
    public void setUp() throws Exception {
        clearTable("Payment");
        PaymentEntity payment = new PaymentEntity();
        payment.setFio("Тестовый человек");
        payment.setPrice(1300);
        payment.setPriceDone(500);
        payment.setPayType("Квартплата");
        StorePayments.save(payment);
    }
    /**
     * Очистка указанной таблицы
     * @param tableName таблица
     */
    private void clearTable(String tableName) {
        Session session = StorePayments.getSession();
        session.beginTransaction();
        String hql = String.format("delete from %s", tableName + "Entity");
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.flush();
        session.getTransaction().commit();
        session.close();
    }
    /**
     * Тест получения списка объектов
     * @throws Exception исключение
     */
    @Test
    public void testAllObjectWithClass() throws Exception {
        Object[] payments = StorePayments.allObjectWithClass(PaymentEntity.class).toArray();
        Assert.assertFalse("More when one space founded!", testStore(payments));

    }

    private boolean testStore(Object[] payments){
        for(PaymentEntity payment : (PaymentEntity[]) payments){
            if(payment.getFio().equals("Тестовый человек"))
                if(payment.getPrice() == 1300)
                    if(payment.getPriceDone() == 500)
                        if(payment.getPayType().equals("Квартплата")) {
                            System.out.print("Hey");
                            return false;
                        }
        }

        return true;
    }

}
