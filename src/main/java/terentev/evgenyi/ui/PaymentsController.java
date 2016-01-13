package terentev.evgenyi.ui;

import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Окно, отображающее все счета.
 */
public class PaymentsController extends JFrame {

    private JTable tablePayments;

    public PaymentsController() {
        initComponents();
        setPreferredSize(new Dimension(300,300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initComponents() {
        setTitle("Payments");
        setMenuBar();
        tablePayments = new JTable();
        displayResult(StorePayments.allObjectWithClass(PaymentEntity.class));
        getContentPane().add(tablePayments);
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Menu menu = new Menu();
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void displayResult(java.util.List<PaymentEntity> items) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("ФИО");
        tableHeaders.add("Суммаплатежа");
        tableHeaders.add("Оплачено");
        tableHeaders.add("Видплатежа");

        for(PaymentEntity payment : items) {
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(payment.getFio());
            oneRow.add(payment.getPrice());
            oneRow.add(payment.getPriceDone());
            oneRow.add(payment.getPayType());
            tableData.add(oneRow);
        }
        tablePayments.setModel(new DefaultTableModel(tableData, tableHeaders));
    }

    public static void main(String[] args) {
        new PaymentsController();
    }
}
