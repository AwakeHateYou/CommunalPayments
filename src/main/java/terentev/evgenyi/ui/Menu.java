package terentev.evgenyi.ui;

import terentev.evgenyi.model.PaymentEntity;

import javax.swing.*;
import terentev.evgenyi.store.StorePayments;

/**
 * Меню с действиями.
 * @autor Терентьев Евгений
 */
public class Menu extends JMenu {
    /**
     * Элементы меню.
     */
    JMenuItem addPayment, sumPaymentByFIO, listDebtors;
    /**
     * Ссылка на главное окно.
     */
    PaymentsController mainWindow;

    /**
     * Конструктор.
     * @param mainWindow ссылка на главное окно.
     */
    public Menu(PaymentsController mainWindow) {
        this.mainWindow = mainWindow;
        initComponents();
        add(addPayment);
        add(sumPaymentByFIO);
        add(listDebtors);
        setVisible(true);
    }

    /**
     * Инициализация компонентов меню.
     */
    private void initComponents() {
        this.setText("Меню");
        addPayment = new JMenuItem("Добавить платеж");
        sumPaymentByFIO = new JMenuItem("Вывести счета по ФИО");
        listDebtors = new JMenuItem("Вывести список должников");
        bind();
    }

    /**
     * Назначение действий кнопок.
     */
    private void bind() {
        addPayment.addActionListener(e -> {
            CreatePaymentController window = new CreatePaymentController(mainWindow);
            window.pack();
            window.setVisible(true);
        });
        listDebtors.addActionListener(e -> {
            CreateDebtorsListController window = new CreateDebtorsListController(mainWindow);
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
