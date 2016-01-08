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
    public CreateDebtorsListController(){
        initialize();
    }
    private void initialize(){
        setLayout(new FlowLayout());
        listDebtors = new JList<>();
        from = new JSpinner();
        to = new JSpinner();
        accept = new JButton("Приянть");
        findDebtors();
        getContentPane().add(from);
        getContentPane().add(to);
        getContentPane().add(accept);
        getContentPane().add(listDebtors);
    }
    /**
     * Поиск всех долждников
     * @param departure место отправления
     * @param varParameter второй параметр
     */
    private void findDebtors() {
        Session session = StorePayments.getSession();

        String queryString = "select fio from PaymentEntity payment where payment.price > payment.priceDone order by payment.fio";
//        if (placeRadioButton.isSelected()) {
//            queryString += "train.destinationLocation = :varParameter";
//        } else {
//            queryString += "train.arrivalTime = :varParameter";
//        }

        Query query = session.createQuery(queryString);
        listDebtors.setListData(query.list().toArray());

        session.close();
    }

}
