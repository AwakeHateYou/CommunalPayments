package ui;
import javax.swing.*;

/**
 * Меню с действиями.
 */
public class Menu extends JMenu {
    JMenuItem addPayment, sumPaymentByFIO, listDebtors, listDebtorsInRange, payThePrice;
    public Menu() {
        initComponents();
        add(addPayment);
        add(sumPaymentByFIO);
        add(listDebtors);
        add(listDebtorsInRange);
        add(payThePrice);
        setVisible(true);

    }

    private void initComponents() {
        this.setText("Меню");
        addPayment = new JMenuItem("Добавить платеж");
        sumPaymentByFIO = new JMenuItem("Вывести счета по ФИО");
        listDebtors = new JMenuItem("Вывести список должников");
        listDebtorsInRange = new JMenuItem("Вывести список должников в диапазоне");
        payThePrice = new JMenuItem("Оплатить по счету");
        bind();
    }
    private void bind(){
        addPayment.addActionListener(e -> {
            CreatePaymentController window = new CreatePaymentController();
            //window.setTrainListModel(trainsListModel);
            window.pack();
            window.setVisible(true);
        });
    }
}
