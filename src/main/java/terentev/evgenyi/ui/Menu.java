package terentev.evgenyi.ui;

import terentev.evgenyi.model.PaymentEntity;

import javax.swing.*;
import terentev.evgenyi.store.StorePayments;

/**
 * Меню с действиями.
 */
public class Menu extends JMenu {
    JMenuItem addPayment, deletePayment, sumPaymentByFIO, listDebtors, listDebtorsInRange, payThePrice;
    PaymentsController mainWindow;

    public Menu(PaymentsController mainWindow) {
        this.mainWindow = mainWindow;
        initComponents();
        add(addPayment);
        add(sumPaymentByFIO);
        add(listDebtors);
        setVisible(true);
    }

    private void initComponents() {
        this.setText("Меню");
        addPayment = new JMenuItem("Добавить платеж");
        sumPaymentByFIO = new JMenuItem("Вывести счета по ФИО");
        listDebtors = new JMenuItem("Вывести список должников");
        bind();
    }

    private void bind() {
        addPayment.addActionListener(e -> {
            CreatePaymentController window = new CreatePaymentController(mainWindow);
            window.pack();
            window.setVisible(true);
        });
        listDebtors.addActionListener(e -> {
            CreateDebtorsListController window = new CreateDebtorsListController();
            window.pack();
            window.setVisible(true);
        });
        sumPaymentByFIO.addActionListener(e -> {
            SearchPaymentsByFIOController window = new SearchPaymentsByFIOController();
            window.pack();
            window.setVisible(true);
        });
    }

    /**
     * Показывает сообщение с информацией
     * @param message сообщение
     */
    private void showMessage(String message) {
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "info", JOptionPane.INFORMATION_MESSAGE);
    }
}
