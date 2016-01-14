package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;
import terentev.evgenyi.util.EmptyFieldException;
import terentev.evgenyi.util.NotAPositiveValueException;
import terentev.evgenyi.util.PayOverPriceException;

import javax.swing.*;
import java.awt.*;

/**
 * Окно с возможностью оплаты счета.
 */
public class PayThePriceController extends JFrame{
    /**
     * Поле с ценой.
     */
    private JTextField price;
    /**
     * Кнопки.
     */
    private JButton ok, cancel;

    /**
     * Конструктор.
     * @param tablePayments таблица с счетами
     */
    public PayThePriceController(JTable tablePayments){
        setLayout(new GridLayout(2, 1));
        initialize();
        bind(tablePayments);

    }

    /**
     * Инициализация полей.
     */
    private void initialize(){
        initPrice();
        initButtons();
    }

    /**
     * Инициализация цены.
     */
    private void initPrice(){
        price = new JTextField();
        price.setPreferredSize(new Dimension(24, 24));
        getContentPane().add(price);
    }

    /**
     * Инициализация кнопок.
     */
    private void initButtons(){
        ok = new JButton("Принять");
        cancel = new JButton("Отменить");
        JPanel buttons = new JPanel();
        buttons.add(ok);
        buttons.add(cancel);
        getContentPane().add(buttons);
    }

    /**
     * Назначение кнопок.
     * @param tablePayments таблица платежей
     */
    private void bind(JTable tablePayments){
        ok.addActionListener(e -> {
            payThePrice(tablePayments);
            dispose();
        });
        cancel.addActionListener(e -> {
            PayThePriceController.this.setVisible(false);
            PayThePriceController.this.dispose();
        });
    }

    /**
     * Оплата выделенного в таблице счета.
     * @param tablePayments таблица
     */
    private void payThePrice(JTable tablePayments){
        try{
            checkValues(tablePayments);
            String pay = price.getText();
            Session session = StorePayments.getSession();
            String queryString = "from PaymentEntity where id = :ID";
            Query query = session.createQuery(queryString);
            query.setParameter("ID", tablePayments.getValueAt(tablePayments.getSelectedRow(), 0));
            PaymentEntity payment = (PaymentEntity) query.uniqueResult();
            payment.setPriceDone(Double.parseDouble(pay) + (Double.parseDouble(tablePayments.getValueAt(tablePayments.getSelectedRow(), 3).toString())));
            tablePayments.setValueAt(Double.parseDouble(pay) + (Double.parseDouble(tablePayments.getValueAt(tablePayments.getSelectedRow(), 3).toString())),
                    tablePayments.getSelectedRow(), 3);
            StorePayments.save(query.uniqueResult());
            session.close();
        }catch (Exception e){
            catchException(e);
        }
    }

    /**
     * Проверка полей.
     * @param tablePayments таблица счетов
     * @throws Exception
     */
    private void checkValues(JTable tablePayments) throws Exception{
        if(price.getText().isEmpty())
            throw  new EmptyFieldException();
        if(Double.parseDouble(price.getText()) <= 0)
            throw  new NotAPositiveValueException();
        if(Double.parseDouble(tablePayments.getValueAt(tablePayments.getSelectedRow(), 2).toString())
                < (Double.parseDouble(tablePayments.getValueAt(tablePayments.getSelectedRow(), 3).toString()) + Double.parseDouble(price.getText())))
            throw new PayOverPriceException();
    }
    /**
     * Ловит все исключения.
     * @param e - NumberFormatException, NotAPositiveValueException, WrongAmpuntNeighboursException.
     */
    private static void catchException(Exception e) {
        new JOptionPane().showMessageDialog(null, e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
    }
}
