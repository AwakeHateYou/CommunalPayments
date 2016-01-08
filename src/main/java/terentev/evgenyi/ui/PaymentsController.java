package terentev.evgenyi.ui;

import terentev.evgenyi.model.PaymentEntity;

import javax.swing.*;
import java.awt.*;

/**
 * Окно, отображающее все счета.
 */
public class PaymentsController extends JFrame {
    private JScrollPane scrollPayments;
    private JList<PaymentEntity> listPayments;

    private DefaultListModel<PaymentEntity> paymentsListModel;

    public PaymentsController() {
        initComponents();
        setPreferredSize(new Dimension(300,300));
//        updateListWithItems(listPayments, StorePayments.allObjectWithClass(PaymentEntity.class));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initComponents() {
        setTitle("Payments");
        setMenuBar();
        scrollPayments = new JScrollPane();
        scrollPayments.add(new JLabel("Test"));

        getContentPane().add(scrollPayments);
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Menu menu = new Menu();
        menu.setPaymentsListModel(paymentsListModel);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

//    /**
//     * Добавляет объекты в список
//     * @param listItems список
//     * @param items объекты
//     */
//    private void updateListWithItems(JList<PaymentEntity> listItems, java.util.List<PaymentEntity> items) {
//        DefaultListModel<PaymentEntity> model = new DefaultListModel<>();
//        items.forEach(model::addElement);
//
//        paymentsListModel = model;
//        listItems.setModel(model);
//    }

    public static void main(String[] args) {
        new PaymentsController();
    }
}
