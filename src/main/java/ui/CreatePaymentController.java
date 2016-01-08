package ui;
import javax.swing.*;
import java.awt.*;

/**
 * Окно создания счета.
 */
public class CreatePaymentController extends JFrame{
    private JTextField fio, price, priceDone;
    private JButton accept, cancel;
    private JComboBox typeList;
    private String[] defaultPayType = {"Квартплата", "Плата за электроэнергию", "Плата за телефон"};
    public CreatePaymentController(){
        initialize();
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(350, 280));
        setResizable(false);

    }
    private void initialize(){
        fio = new JTextField();
        price = new JTextField();
        priceDone = new JTextField();
        typeList = new JComboBox(defaultPayType);
        accept = new JButton("Принять");
        cancel = new JButton("Отменить");
        fio.setPreferredSize(new Dimension(200, 24));
        price.setPreferredSize(new Dimension(200, 24));
        priceDone.setPreferredSize(new Dimension(200, 24));

        JPanel fioPanel = new JPanel();
        JPanel pricePanel = new JPanel();
        JPanel priceDonePanel = new JPanel();
        JPanel typePanel = new JPanel();
        fioPanel.add(new JLabel("Ф.И.О."));
        fioPanel.add(fio);
        pricePanel.add(new JLabel("Сумма платежа"));
        pricePanel.add(price);
        priceDonePanel.add(new JLabel("Внесенная сумма"));
        priceDonePanel.add(priceDone);
        typePanel.add(new JLabel("Вид платежа"));
        typePanel.add(typeList);
        add(fioPanel);
        add(pricePanel);
        add(priceDonePanel);
        add(typePanel);
        add(accept);
        add(cancel);
    }
}
