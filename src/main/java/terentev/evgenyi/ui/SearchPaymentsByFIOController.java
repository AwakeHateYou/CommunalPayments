package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Вывод списка счетов по ФИО с подсчетом суммы платежей.
 */
public class SearchPaymentsByFIOController extends JFrame{
    private JComboBox<Object> fiosList;
    private JScrollPane scrollPane;
    private JTable sortedTable;
    private JTextField sumPayments;
    public SearchPaymentsByFIOController(){
        setLayout(new BorderLayout());
        initialize();
        fillTablePayments(StorePayments.allObjectWithClass(PaymentEntity.class));
        setPreferredSize(new Dimension(300, 300));
        bind();
    }
    private void initialize(){
        initFioList();
        initScrollPane();
        initSumPane();
    }
    private void initFioList(){
        fiosList = new JComboBox<>(fillFioList());
        fiosList.setPreferredSize(new Dimension(200, 24));
        getContentPane().add(fiosList, BorderLayout.NORTH);

    }
    private void initScrollPane(){
        sortedTable = new JTable();
        scrollPane = new JScrollPane(sortedTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    public void initSumPane(){
        JPanel sumPanel = new JPanel();
        sumPayments = new JTextField();
        sumPayments.setPreferredSize(new Dimension(100, 24));
        sumPanel.add(new JLabel("Сумма платежей: "));
        sumPanel.add(sumPayments);
        getContentPane().add(sumPanel, BorderLayout.SOUTH);
    }
    private void bind(){
        fiosList.addActionListener(e -> fillTablePayments(StorePayments.allObjectWithClass(PaymentEntity.class)));
    }
    private Object[] fillFioList(){
        Object[] listFios;
        Session session = StorePayments.getSession();
        String queryString = "select distinct fio from PaymentEntity";
        Query query = session.createQuery(queryString);
        listFios = query.list().toArray();
        session.close();
        return listFios;
    }
    private void fillTablePayments(java.util.List<PaymentEntity> items){
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("ФИО");
        tableHeaders.add("Сумма платежа");
        tableHeaders.add("Оплачено");
        tableHeaders.add("Вид платежа");
        for(PaymentEntity payment : items) {
            if(payment.getFio().equals(fiosList.getSelectedItem())) {
                Vector<Object> oneRow = new Vector<>();
                oneRow.add(payment.getFio());
                oneRow.add(payment.getPrice());
                oneRow.add(payment.getPriceDone());
                oneRow.add(payment.getPayType());
                tableData.add(oneRow);
            }
        }
        sortedTable.setModel(new DefaultTableModel(tableData, tableHeaders));
        generateSumPayments();
    }
    private void generateSumPayments(){
        Double sum = 0.0;
        for(int i = 0; i < sortedTable.getRowCount(); i++){
            sum += Double.parseDouble(sortedTable.getValueAt(i, 2).toString());
        }
        sumPayments.setText(sum.toString());
    }
}
