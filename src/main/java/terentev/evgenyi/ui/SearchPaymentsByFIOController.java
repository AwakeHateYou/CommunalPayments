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
 * @author Терентьев Евгений
 */
public class SearchPaymentsByFIOController extends JFrame{
    /**
     * Выбор ФИО
     */
    private JComboBox<Object> fiosList;
    /**
     * Место для таблицы.
     */
    private JScrollPane scrollPane;
    /**
     * Таблица.
     */
    private JTable sortedTable;
    /**
     * Сумма платежей.
     */
    private JTextField sumPayments;

    /**
     * Конструктор.
     */
    public SearchPaymentsByFIOController(){
        setLayout(new BorderLayout());
        initialize();
        fillTablePayments(StorePayments.allObjectWithClass(PaymentEntity.class));
        setPreferredSize(new Dimension(300, 300));
        bind();
    }

    /**
     * Инициализация коспонентов окна.
     */
    private void initialize(){
        initFioList();
        initScrollPane();
        initSumPane();
    }

    /**
     * Инициализация выбора ФИО.
     */
    private void initFioList(){
        fiosList = new JComboBox<>(fillFioList());
        fiosList.setPreferredSize(new Dimension(200, 24));
        getContentPane().add(fiosList, BorderLayout.NORTH);

    }

    /**
     * Инициализация таблицы.
     */
    private void initScrollPane(){
        sortedTable = new JTable(){
            @Override
            public boolean isCellEditable ( int row, int column )
            {
                return false;
            }
        };
        sortedTable.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(sortedTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Инициализация окна с суммой.
     */
    public void initSumPane(){
        JPanel sumPanel = new JPanel();
        sumPayments = new JTextField();
        sumPayments.setPreferredSize(new Dimension(100, 24));
        sumPayments.setEditable(false);
        sumPanel.add(new JLabel("Сумма платежей: "));
        sumPanel.add(sumPayments);
        getContentPane().add(sumPanel, BorderLayout.SOUTH);
    }

    /**
     * Назначение кнопок.
     */
    private void bind(){
        fiosList.addActionListener(e -> fillTablePayments(StorePayments.allObjectWithClass(PaymentEntity.class)));
    }

    /**
     * Заполнение массива уникальных ФИО
     * @return массив ФИО
     */
    private Object[] fillFioList(){
        Object[] listFios;
        Session session = StorePayments.getSession();
        String queryString = "select distinct fio from PaymentEntity";
        Query query = session.createQuery(queryString);
        listFios = query.list().toArray();
        session.close();
        return listFios;
    }

    /**
     * Заполнение таблицы.
     * @param items база
     */
    private void fillTablePayments(java.util.List<PaymentEntity> items){
        Vector<String> tableHeaders = new Vector<>();
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

    /**
     * Создание суммы платежей.
     */
    private void generateSumPayments(){
        Double sum = 0.0;
        for(int i = 0; i < sortedTable.getRowCount(); i++){
            sum += Double.parseDouble(sortedTable.getValueAt(i, 2).toString());
        }
        sumPayments.setText(sum.toString());
    }
}
