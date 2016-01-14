package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Vector;

/**
 * Окно, отображающее все счета.
 * @autor Терентьев Евгений
 */
public class PaymentsController extends JFrame {
    /**
     * Геттер для таблицы.
     * @return таблица с счетами
     */
    public JTable getTablePayments() {
        return tablePayments;
    }

    /**
     * Таблица со счетами.
     */
    private JTable tablePayments;
    private JScrollPane scrollPane;
    /**
     * Кнопки.
     */
    private JButton pay, delete;

    /**
     * Конструктор.
     */
    public PaymentsController() {
        initComponents();
        setPreferredSize(new Dimension(500, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Инициализация компонентов.
     */
    private void initComponents() {
        setTitle("Payments");
        pay = new JButton("Оплатить счет");
        delete = new JButton("Удалить счет");
        setMenuBar();
        setUpTable();
        bind();
    }

    /**
     * Размещение и настройка таблицы.
     */
    private void setUpTable(){
        tablePayments = new JTable(){
            @Override
            public boolean isCellEditable ( int row, int column )
            {
                return false;
            }
        };
        tablePayments.getTableHeader().setReorderingAllowed(false);
        displayResult(StorePayments.allObjectWithClass(PaymentEntity.class));
        scrollPane = new JScrollPane(tablePayments);
        getContentPane().add(scrollPane);
    }

    /**
     * Разместить меню.
     */
    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Menu menu = new Menu(this);
        menuBar.add(menu);
        menuBar.add(pay);
        menuBar.add(delete);
        setJMenuBar(menuBar);
    }

    /**
     * Назначить кнопки.
     */
    private void bind(){
            delete.addActionListener(e -> deletePayment());
            pay.addActionListener(e -> payThePrice());
    }

    /**
     * Обновить таблицу
     * @param items база
     */
    public void updateTable(java.util.List<PaymentEntity> items){
        displayResult(items);
    }

    /**
     * Отобразить базу в таблицу.
     * @param items база
     */
    private void displayResult(java.util.List<PaymentEntity> items) {
        Vector<String> tableHeaders = new Vector<>();
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

    /**
     * Оплатить выбраный платеж.
     */
    private void payThePrice(){
        if (tablePayments.getSelectedRow() >= 0) {
            PayThePriceController window = new PayThePriceController(tablePayments);
            window.pack();
            window.setVisible(true);
        }else{
            showMessage();
        }
    }

    /**
     * Удалить выбранные платеж из базы.
     */
    private void deletePayment() {
        if(tablePayments.getSelectedRow() >= 0) {
            Session session = StorePayments.getSession();
            String queryString = "from PaymentEntity where id = :ID";
            Query query = session.createQuery(queryString);
            query.setParameter("ID", tablePayments.getValueAt(tablePayments.getSelectedRow(), 0));
            StorePayments.deleteObject(query.uniqueResult());
            updateTable(StorePayments.allObjectWithClass(PaymentEntity.class));
            session.close();
        }else{
            showMessage();
        }
    }

    /**
     * Показывает информационное сообщение.
     */
    private void showMessage(){
        new JOptionPane().showMessageDialog(null, "Необходимо выделить счет!", "Alert", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Сокрытие первого столбца.
     */
    private void blockIdColumn(){
        TableColumnModel cm = tablePayments.getColumnModel();
        cm.getColumn(0).setMaxWidth(0);
        cm.getColumn(0).setResizable(false);
    }

    /**
     * Точка входа в программу.
     * @param args
     */
    public static void main(String[] args) {
        new PaymentsController();
    }
}
