package ui;
import javax.swing.*;
import java.awt.*;

/**
 * Окно создания счета.
 */
public class CreatePaymentController extends JFrame{

    public CreatePaymentController() {
        getContentPane().setLayout(new GridLayout(5, 1));
        initComponents();
        setPreferredSize(new Dimension(350, 280));
        setResizable(false);
    }

    private void initComponents() {
        setTitle("Добавление платежа");
        setFioLayout();
        setPriceLayout();
        setPriceDefaultLayout();
        setTypeLayout();
        setButtonLayout();
        pack();
    }

    private void setFioLayout() {
        JPanel fioPanel = new JPanel();
        JTextField fio = new JTextField();
        fio.setPreferredSize(new Dimension(200, 24));
        fioPanel.add(new JLabel("Ф.И.О."));
        fioPanel.add(fio);
        getContentPane().add(fioPanel);
    }

    private void setPriceLayout() {
        JPanel pricePanel = new JPanel();
        JTextField price = new JTextField();
        price.setPreferredSize(new Dimension(200, 24));
        pricePanel.add(new JLabel("Сумма платежа"));
        pricePanel.add(price);
        getContentPane().add(pricePanel);
    }

    private void setPriceDefaultLayout() {
        JPanel priceDonePanel = new JPanel();
        JTextField priceDone = new JTextField();
        priceDone.setPreferredSize(new Dimension(200, 24));
        priceDonePanel.add(new JLabel("Внесенная сумма"));
        priceDonePanel.add(priceDone);
        getContentPane().add(priceDonePanel);
    }

    private void setTypeLayout() {
        JPanel typePanel = new JPanel();
        JComboBox<String> typeList = new JComboBox<>(
                new String[] {"Квартплата", "Плата за электроэнергию", "Плата за телефон"});
        typePanel.add(new JLabel("Вид платежа"));
        typePanel.add(typeList);
        getContentPane().add(typePanel);
    }

    private void setButtonLayout() {
        JPanel buttonPanel = new JPanel();
        JButton accept = new JButton("Принять");
        JButton cancel = new JButton("Отменить");
        buttonPanel.add(accept);
        buttonPanel.add(cancel);
        getContentPane().add(buttonPanel);
    }

}
