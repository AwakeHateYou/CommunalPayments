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
     * Тест создания сесссии
     * @throws Exception исключение
     */
    @Test
    public void testGetSession() throws Exception {
        Session session = StorePayments.getSession();
        assertNotNull(session);
        session.close();
    }
    /**
     * Тест сохранения
     * @throws Exception исключение
     */
    @Test
    public void testSave() throws Exception {
        assertEquals(1, StorePayments.allObjectWithClass(PaymentEntity.class).toArray().length);
    }
    /**
     * Тест удаления объекта из базы
     * @throws Exception исключение
     */
    @Test
    public void testDeleteObject() throws Exception {
        Object[] payments = StorePayments.allObjectWithClass(PaymentEntity.class).toArray();
        assertEquals(1, payments.length);

        PaymentEntity payment = new PaymentEntity();
        payment.setFio("Тестовый человек 2");
        payment.setPrice(1300);
        payment.setPriceDone(500);
        payment.setPayType("Квартплата");
        StorePayments.save(payment);

        payments = StorePayments.allObjectWithClass(PaymentEntity.class).toArray();
        assertEquals(2, payments.length);

        StorePayments.deleteObject(payments[payments.length - 1]);

        payments = StorePayments.allObjectWithClass(PaymentEntity.class).toArray();
        assertEquals(1, payments.length);
    }

}
