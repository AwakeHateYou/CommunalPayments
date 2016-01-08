package terentev.evgenyi.ui;

import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;

import javax.swing.*;
import java.awt.*;

/**
 * Окно создания счета.
 */
public class CreatePaymentController extends JFrame{
    private JTextField fioTextField;
    private JTextField priceTextField;
    private JTextField priceDefaultTextField;
    private JComboBox<String> typeListComboBox;
    private String[] defaultTypes = {"Квартплата", "Плата за электроэнергию", "Плата за телефон"};

    private DefaultListModel<PaymentEntity> paymentEntityDefaultListModel;

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
        fioTextField = new JTextField();
        fioTextField.setPreferredSize(new Dimension(200, 24));
        fioPanel.add(new JLabel("Ф.И.О."));
        fioPanel.add(fioTextField);
        getContentPane().add(fioPanel);
    }

    private void setPriceLayout() {
        JPanel pricePanel = new JPanel();
        priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(200, 24));
        pricePanel.add(new JLabel("Сумма платежа"));
        pricePanel.add(priceTextField);
        getContentPane().add(pricePanel);
    }

    private void setPriceDefaultLayout() {
        JPanel priceDonePanel = new JPanel();
        priceDefaultTextField = new JTextField();
        priceDefaultTextField.setPreferredSize(new Dimension(200, 24));
        priceDonePanel.add(new JLabel("Внесенная сумма"));
        priceDonePanel.add(priceDefaultTextField);
        getContentPane().add(priceDonePanel);
    }

    private void setTypeLayout() {
        JPanel typePanel = new JPanel();
        typeListComboBox = new JComboBox<>(defaultTypes);
        typePanel.add(new JLabel("Вид платежа"));
        typePanel.add(typeListComboBox);
        getContentPane().add(typePanel);
    }

    private void setButtonLayout() {
        JPanel buttonPanel = new JPanel();
        JButton acceptButton = new JButton("Принять");
        JButton cancelButton = new JButton("Отменить");
        acceptButton.addActionListener(e -> acceptListener());
        cancelButton.addActionListener(e -> {
            CreatePaymentController.this.setVisible(false);
            CreatePaymentController.this.dispose();
        });
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel);
    }

    private void acceptListener() {
        String price = priceTextField.getText();
        String priceDefault = priceDefaultTextField.getText();

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setFio(fioTextField.getText());
        paymentEntity.setPrice(Double.parseDouble(price));
        paymentEntity.setPriceDone(Double.parseDouble(priceDefault));
        paymentEntity.setPayType(defaultTypes[typeListComboBox.getSelectedIndex()]);
        StorePayments.save(paymentEntity);
        paymentEntityDefaultListModel.addElement(paymentEntity);
        dispose();
    }

    public void setPaymentEntityDefaultListModel(DefaultListModel<PaymentEntity> paymentEntityDefaultListModel) {
        this.paymentEntityDefaultListModel = paymentEntityDefaultListModel;
    }
}
