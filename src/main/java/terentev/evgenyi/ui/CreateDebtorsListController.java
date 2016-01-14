package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import java.awt.*;

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
        initScrollPane();
        initSpinnersPane();
        initButtonPane();
        findDebtors();
    }
    private void initScrollPane(){
        listDebtors = new JList<>();
        scrollPane = new JScrollPane(listDebtors);
        getContentPane().add(scrollPane);
    }
    private void initSpinnersPane(){
        from = new JSpinner();
        from.setValue(1);
        to = new JSpinner();
        to.setValue(2);
        inRange = new JRadioButton();
        inRange.setSelected(false);
        JPanel spinners = new JPanel();
        spinners.add(inRange);
        spinners.add(from);
        spinners.add(to);
        getContentPane().add(spinners);
    }
    private void initButtonPane(){
        accept = new JButton("Приянть");
        getContentPane().add(accept);
    }
    /**
     * Поиск всех долждников
     */
    private void findDebtors() {
        Session session = StorePayments.getSession();

        String queryString = "select fio from PaymentEntity payment where payment.price > payment.priceDone";
        if (inRange.isSelected()) {
            queryString += " and payment.priceDone > :ID";
        } else {
             queryString += " order by payment.fio";
        }

        Query query = session.createQuery(queryString).setParameter("ID", 10);
//        query.setParameter("ID", from.getValue());
//        query.setParameter("upEdge", Double.parseDouble(to.getValue().toString()));
        listDebtors.setListData(query.list().toArray());

        session.close();
    }

}
