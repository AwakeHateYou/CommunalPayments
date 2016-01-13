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
    private JScrollPane scrollPane;

    public PaymentsController() {
        initComponents();
        setPreferredSize(new Dimension(500, 300));
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
        scrollPane = new JScrollPane(tablePayments);
        getContentPane().add(scrollPane);
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Menu menu = new Menu(this);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    public void updateTable(java.util.List<PaymentEntity> items){
        displayResult(items);
    }
    private void displayResult(java.util.List<PaymentEntity> items) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("ФИО");
        tableHeaders.add("Сумма платежа");
        tableHeaders.add("Оплачено");
        tableHeaders.add("Вид платежа");

        for(PaymentEntity payment : items) {
            Vector<Object> oneRow = new Vector<>();
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
