package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import java.awt.*;

/**
 * Окно с возможностью оплаты счета.
 */
public class PayThePriceController extends JFrame {
    private JTextField price;
    private JButton ok, cancel;
    public PayThePriceController(JTable tablePayments){
        setLayout(new GridLayout(2, 1));
        initialize();
        bind(tablePayments);

    }
    private void initialize(){
        initPrice();
        initButtons();
    }
    private void initPrice(){
        price = new JTextField();
        getContentPane().add(price);
    }
    private void initButtons(){
        ok = new JButton("Принять");
        cancel = new JButton("Отменить");
        JPanel buttons = new JPanel();
        buttons.add(ok);
        buttons.add(cancel);
        getContentPane().add(buttons);
    }
    private void bind(JTable tablePayments){
        ok.addActionListener(e -> payThePrice(tablePayments));
        cancel.addActionListener(e -> {
            PayThePriceController.this.setVisible(false);
            PayThePriceController.this.dispose();
        });
    }
    private void payThePrice(JTable tablePayments) {
        if (tablePayments.getSelectedRow() >= 0) {
            String pay = price.getText();
            Session session = StorePayments.getSession();
            String queryString = "from PaymentEntity where id = :ID";
            Query query = session.createQuery(queryString);
            query.setParameter("ID", tablePayments.getValueAt(tablePayments.getSelectedRow(), 0));
            PaymentEntity payment = (PaymentEntity) query.uniqueResult();
            payment.setPriceDone(Double.parseDouble(pay));
            StorePayments.save(query.uniqueResult());
            //updateTable(StorePayments.allObjectWithClass(PaymentEntity.class));
            session.close();
        }
    }
}
