package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.store.StorePayments;
import terentev.evgenyi.util.NotAPositiveValueException;
import terentev.evgenyi.util.WrongEdgesException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class CreateDebtorsListController extends JFrame{
    private JList<Object> listDebtors;
    private JSpinner from, to;
    private JButton accept;
    private JRadioButton inRange;
    private JScrollPane scrollPane;
    public CreateDebtorsListController(){
        initialize();
    }
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
     * Поиск всех долждников
     */
    private void findDebtors() {
        try {
            checkFieldCorrect();
            Session session = StorePayments.getSession();
            String queryString = "select fio from PaymentEntity payment where payment.price > payment.priceDone";
            if (inRange.isSelected()) {
                queryString += " and payment.price - payment.priceDone >= " + from.getValue() + " and payment.price - payment.priceDone <= " + to.getValue();
            } else {
                queryString += " order by payment.fio";
            }
            Query query = session.createQuery(queryString);
//        query.setParameter("lowEdge", from.getValue());
//        query.setParameter("upEdge", to.getValue());
            listDebtors.setListData(query.list().toArray());

            session.close();
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
    private void checkFieldCorrect() throws Exception{
        if(Double.parseDouble(from.getValue().toString()) < 0 || Double.parseDouble(to.getValue().toString()) <= 0)
            throw new NotAPositiveValueException();
        if(Double.parseDouble(from.getValue().toString()) > Double.parseDouble(to.getValue().toString()))
            throw new WrongEdgesException();

    }
    /**
     * Ловит все исключения.
     * @param e - NumberFormatException, NotAPositiveValueException, WrongAmpuntNeighboursException.
     */
    private static void catchException(Exception e) {
        new JOptionPane().showMessageDialog(null, e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
    }

}
