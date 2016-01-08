package ui;
import model.PaymentEntity;
import store.StorePayments;

import javax.swing.*;
import java.awt.*;

/**
 * Окно, отображающее все счета.
 */
public class PaymentsController extends JPanel {
    private JScrollPane scrollPayments;
    private JList<PaymentEntity> listPayments;
    private DefaultListModel<PaymentEntity> paymentsListModel;
    public PaymentsController(){
        initialize();
        add(scrollPayments);
        setPreferredSize(new Dimension(300,300));
        updateListWithItems(listPayments, StorePayments.allObjectWithClass(PaymentEntity.class));
    }
    private void initialize(){
        scrollPayments = new JScrollPane();
        scrollPayments.add(new JLabel("Test"));
    }
    /**
     * Добавляет объекты в список
     * @param listItems список
     * @param items объекты
     */
    private void updateListWithItems(JList<PaymentEntity> listItems, java.util.List<PaymentEntity> items) {
        DefaultListModel<PaymentEntity> model = new DefaultListModel<>();
        items.forEach(model::addElement);

        paymentsListModel = model;
        listItems.setModel(model);
    }
}
