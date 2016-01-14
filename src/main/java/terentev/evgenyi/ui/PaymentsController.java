package terentev.evgenyi.ui;

import jdk.nashorn.internal.runtime.ECMAException;
import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Locale;
import java.util.Vector;

/**
 * Окно, отображающее все счета.
 */
public class PaymentsController extends JFrame {

    public JTable getTablePayments() {
        return tablePayments;
    }

    private JTable tablePayments;
    private JScrollPane scrollPane;
    private JButton pay, delete;

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

        pay = new JButton("Оплатить счет");
        delete = new JButton("Удалить счет");
        setMenuBar();
        tablePayments = new JTable();
        displayResult(StorePayments.allObjectWithClass(PaymentEntity.class));
        scrollPane = new JScrollPane(tablePayments);
        getContentPane().add(scrollPane);
        bind();
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Menu menu = new Menu(this);
        menuBar.add(menu);
        menuBar.add(pay);
        menuBar.add(delete);
        setJMenuBar(menuBar);
    }
    private void bind(){
            delete.addActionListener(e -> deletePayment());
            pay.addActionListener(e -> payThePrice());
    }
    public void updateTable(java.util.List<PaymentEntity> items){
        displayResult(items);
    }
    private void displayResult(java.util.List<PaymentEntity> items) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("");
        tableHeaders.add("ФИО");
        tableHeaders.add("Сумма платежа");
        tableHeaders.add("Оплачено");
        tableHeaders.add("Вид платежа");

        for(PaymentEntity payment : items) {
            Vector<Object> oneRow = new Vector<>();
            oneRow.add(payment.getId());
            oneRow.add(payment.getFio());
            oneRow.add(payment.getPrice());
            oneRow.add(payment.getPriceDone());
            oneRow.add(payment.getPayType());
            tableData.add(oneRow);
        }

        tablePayments.setModel(new DefaultTableModel(tableData, tableHeaders));
        blockIdColumn();
    }
    private void payThePrice(){
        PayThePriceController window = new PayThePriceController(tablePayments);
        window.pack();
        window.setVisible(true);
    }
    private void deletePayment() {
        if(tablePayments.getSelectedRow() >= 0) {
            Session session = StorePayments.getSession();
            String queryString = "from PaymentEntity where id = :ID";
            Query query = session.createQuery(queryString);
            query.setParameter("ID", tablePayments.getValueAt(tablePayments.getSelectedRow(), 0));
            StorePayments.deleteObject(query.uniqueResult());
            updateTable(StorePayments.allObjectWithClass(PaymentEntity.class));
            session.close();
        }
    }
    private void blockIdColumn(){
        TableColumnModel cm = tablePayments.getColumnModel();
        cm.getColumn(0).setMaxWidth(0);
        cm.getColumn(0).setResizable(false);
    }

    public static void main(String[] args) {
        new PaymentsController();
    }
}
