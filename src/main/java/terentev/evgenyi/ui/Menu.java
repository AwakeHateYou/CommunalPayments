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
        add(deletePayment);
        add(sumPaymentByFIO);
        add(listDebtors);
        add(listDebtorsInRange);
        add(payThePrice);
        setVisible(true);
    }

    private void initComponents() {
        this.setText("Меню");
        addPayment = new JMenuItem("Добавить платеж");
        deletePayment = new JMenuItem("Удалить выбраный платеж");
        sumPaymentByFIO = new JMenuItem("Вывести счета по ФИО");
        listDebtors = new JMenuItem("Вывести список должников");
        listDebtorsInRange = new JMenuItem("Вывести список должников в диапазоне");
        payThePrice = new JMenuItem("Оплатить по счету");
        bind();
    }

    private void bind() {
        addPayment.addActionListener(e -> {
            CreatePaymentController window = new CreatePaymentController(mainWindow);
            //window.setPaymentEntityDefaultListModel();
            window.pack();
            window.setVisible(true);
        });
//        deletePayment.addActionListener(e -> {
//            try {
//                boolean removed = removeSelectedFromList();
//                if (!removed) {
//                    showMessage("Выберете счет.");
//                }
//            } catch (Exception ex) {
//                showMessage(ex.getLocalizedMessage());
//                }
//        });
        listDebtors.addActionListener(e -> {
            CreateDebtorsListController window = new CreateDebtorsListController();
            window.pack();
            window.setVisible(true);
        });
    }

//    /**
//     * Удаляет выбранный в списке объект
//     * @return успешность удаления
//     */
//    private boolean removeSelectedFromList() {
//        Object selected = listPayments.getSelectedValue();
//
//        if (selected == null) {
//            return false;
//        }
//
//        StorePayments.deleteObject(selected);
//        paymentsListModel.remove(listPayments.getSelectedIndex());
//
//        return true;
//    }
//
//    public void setPaymentsListModel(DefaultListModel<PaymentEntity> paymentsListModel) {
//        this.paymentsListModel = paymentsListModel;
//    }
    /**
     * Показывает сообщение с информацией
     * @param message сообщение
     */
    private void showMessage(String message) {
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "info", JOptionPane.INFORMATION_MESSAGE);
    }
}
