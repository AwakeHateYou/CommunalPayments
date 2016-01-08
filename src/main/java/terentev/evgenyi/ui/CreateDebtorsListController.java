package terentev.evgenyi.ui;

import org.hibernate.Query;
import org.hibernate.Session;
import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;

public class CreateDebtorsListController extends JFrame{
    JList<String> listDebtors;
    private JList<Object> resultList;
    DefaultListModel<String> modelForListDebtors;
    public CreateDebtorsListController(){
        initialize();
    }
    private void initialize(){
        resultList = new JList<>();
        //modelForListDebtors = new DefaultListModel<>();
        findDebtors();
        getContentPane().add(resultList);
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
        resultList.setListData(query.list().toArray());

        session.close();
    }

}
