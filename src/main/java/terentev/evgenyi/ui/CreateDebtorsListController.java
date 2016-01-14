package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.store.StorePayments;
import terentev.evgenyi.util.NotAPositiveValueException;
import terentev.evgenyi.util.WrongEdgesException;

import javax.swing.*;
import java.awt.*;

/**
 * Окно, выводящее список должников в диапазоне.
 * @autor Терентьев Евгений
 */
public class CreateDebtorsListController extends JFrame{
    /**
     * Cписок должников, выведенный на экран.
     */
    private JList<Object> listDebtors;
    /**
     * Переменные диапазона.
     */
    private JSpinner from, to;
    private JButton accept;
    /**
     * Выбрать в диапазоне.
     */
    private JRadioButton inRange;
    /**
     * Место для таблицы.
     */
    private JScrollPane scrollPane;
    /**
     * Ссылка на главное окно.
     */
    private PaymentsController mainWindow;
    public CreateDebtorsListController(PaymentsController mainWindow){
        this.mainWindow = mainWindow;
        initialize();
    }

    /**
     * Инициализация окна с списком должников.
     */
    private void initialize(){
        setLayout(new GridLayout(3, 1));
        setPreferredSize(new Dimension(250, 300));
        initScrollPane();
        initSpinnersPane();
        initButtonPane();
        findDebtors();
        bind();
    }

    /**
     * Инициализация панели с отображение должников.
     */
    private void initScrollPane(){
        listDebtors = new JList<>();
        scrollPane = new JScrollPane(listDebtors);
        getContentPane().add(scrollPane);
    }

    /**
     * Инициализация панели с выбором размера долга.
     */
    private void initSpinnersPane(){
        from = new JSpinner();
        from.setValue(1);
        from.setEnabled(false);
        from.setPreferredSize(new Dimension(75, 24));
        to = new JSpinner();
        to.setPreferredSize(new Dimension(75, 24));
        to.setValue(2);
        to.setEnabled(false);
        inRange = new JRadioButton();
        inRange.setSelected(false);
        JPanel spinners = new JPanel();
        spinners.add(new JLabel("Выбор с размером долга "));
        spinners.add(inRange);
        spinners.add(from);
        spinners.add(to);
        getContentPane().add(spinners);
    }

    /**
     * Инициализация кнопок.
     */
    private void initButtonPane(){
        accept = new JButton("Приянть");
        accept.setPreferredSize(new Dimension(50, 24));
        getContentPane().add(accept);
    }

    /**
     * Назначение действий кнопкам.
     */
    private void bind(){
        accept.addActionListener(e -> findDebtors());
        inRange.addActionListener(e -> activateSpinners());
    }
    /**
     * Поиск всех долждников в заданном интервале.
     */
    private void findDebtors() {
        try {
            checkFieldCorrect();
            DefaultListModel<Object> listDebtorsModel = new DefaultListModel<>();
            Object[] fios = uniqFIOs();
            for(int i = 0; i < fios.length; i++) {
                double paySum = 0;
                double doneSum = 0;
                for (int j = 0; j < mainWindow.getTablePayments().getRowCount(); j++) {
                    if (mainWindow.getTablePayments().getValueAt(j, 1).toString().equals(fios[i].toString())) {
                        paySum += Double.parseDouble(mainWindow.getTablePayments().getValueAt(j, 2).toString());
                        doneSum += Double.parseDouble(mainWindow.getTablePayments().getValueAt(j, 3).toString());
                    }
                }
                if(inRange.isSelected()){
                    double diff = paySum - doneSum;
                    if (diff >= Double.parseDouble(from.getValue().toString()) && diff <= Double.parseDouble(to.getValue().toString())) {
                        listDebtorsModel.addElement(fios[i]);
                    }
                }else {
                    if (paySum > doneSum) {
                        listDebtorsModel.addElement(fios[i]);
                    }
                }
                listDebtors.setModel(listDebtorsModel);
            }
        }catch (Exception e){
            catchException(e);
        }

    }

    /**
     * Включение и выключение полей с размером долга.
     */
    private void activateSpinners(){
        if(inRange.isSelected()) {
            from.setEnabled(true);
            to.setEnabled(true);
        }else{
            from.setEnabled(false);
            to.setEnabled(false);
        }
    }

    /**
     * Проверка введенных данных.
     * @throws Exception
     */
    private void checkFieldCorrect() throws Exception{
        if(Double.parseDouble(from.getValue().toString()) < 0 || Double.parseDouble(to.getValue().toString()) <= 0)
            throw new NotAPositiveValueException();
        if(Double.parseDouble(from.getValue().toString()) > Double.parseDouble(to.getValue().toString()))
            throw new WrongEdgesException();

    }

    /**
     * Нахождение уникальных ФИО.
     * @return массив уникальных ФИО
     */
    private Object[] uniqFIOs(){
        Object[] listFios;
        Session session = StorePayments.getSession();
        String queryString = "select distinct fio from PaymentEntity order by fio";
        Query query = session.createQuery(queryString);
        listFios = query.list().toArray();
        session.close();
        return listFios;
    }
    /**
     * Ловит все исключения.
     * @param e - NumberFormatException, NotAPositiveValueException, WrongAmpuntNeighboursException.
     */
    private static void catchException(Exception e) {
        new JOptionPane().showMessageDialog(null, e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
    }

}
