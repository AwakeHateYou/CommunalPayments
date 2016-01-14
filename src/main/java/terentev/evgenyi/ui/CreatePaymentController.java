package terentev.evgenyi.ui;

import terentev.evgenyi.model.PaymentEntity;
import terentev.evgenyi.store.StorePayments;
import terentev.evgenyi.util.EmptyFieldException;
import terentev.evgenyi.util.NotAPositiveValueException;
import terentev.evgenyi.util.PayOverPriceException;

import javax.swing.*;
import java.awt.*;

/**
 * Окно создания счета.
 */
public class CreatePaymentController extends JFrame{
    private JTextField fioTextField;
    private JTextField priceTextField;
    private JTextField priceDoneTextField;
    private JComboBox<String> typeListComboBox;
    private PaymentsController mainWindow;
    private String[] defaultTypes = {"Квартплата", "Плата за электроэнергию", "Плата за телефон"};

    public CreatePaymentController(PaymentsController mainWindow) {
        this.mainWindow = mainWindow;
        getContentPane().setLayout(new GridLayout(5, 1));
        initComponents();
        setPreferredSize(new Dimension(350, 280));
        setResizable(false);
    }

    private void initComponents() {
        setTitle("Добавление платежа");
        initFioLayout();
        initPriceLayout();
        initPriceDefaultLayout();
        initTypeLayout();
        initButtonLayout();
        pack();
    }

    private void initFioLayout() {
        JPanel fioPanel = new JPanel();
        fioTextField = new JTextField();
        fioTextField.setPreferredSize(new Dimension(200, 24));
        fioPanel.add(new JLabel("Ф.И.О."));
        fioPanel.add(fioTextField);
        getContentPane().add(fioPanel);
    }

    private void initPriceLayout() {
        JPanel pricePanel = new JPanel();
        priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(200, 24));
        pricePanel.add(new JLabel("Сумма платежа"));
        pricePanel.add(priceTextField);
        getContentPane().add(pricePanel);
    }

    private void initPriceDefaultLayout() {
        JPanel priceDonePanel = new JPanel();
        priceDoneTextField = new JTextField();
        priceDoneTextField.setPreferredSize(new Dimension(200, 24));
        priceDonePanel.add(new JLabel("Внесенная сумма"));
        priceDonePanel.add(priceDoneTextField);
        getContentPane().add(priceDonePanel);
    }

    private void initTypeLayout() {
        JPanel typePanel = new JPanel();
        typeListComboBox = new JComboBox<>(defaultTypes);
        typePanel.add(new JLabel("Вид платежа"));
        typePanel.add(typeListComboBox);
        getContentPane().add(typePanel);
    }

    private void initButtonLayout() {
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
        try {
            String price = priceTextField.getText();
            String priceDone = priceDoneTextField.getText();
            checkFieldCorrect(price, priceDone);
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setFio(fioTextField.getText());
            paymentEntity.setPrice(Double.parseDouble(price));
            paymentEntity.setPriceDone(Double.parseDouble(priceDone));
            paymentEntity.setPayType(defaultTypes[typeListComboBox.getSelectedIndex()]);
            StorePayments.save(paymentEntity);
            mainWindow.updateTable(StorePayments.allObjectWithClass(PaymentEntity.class));
            dispose();
        }catch (Exception e){
            catchException(e);
        }

    }
    private void checkFieldCorrect(String price, String priceDone) throws Exception{
        if(fioTextField.getText().isEmpty())
            throw new EmptyFieldException();
        if(Double.parseDouble(price) < Double.parseDouble(priceDone)){
            throw new PayOverPriceException();
        }
        if(Double.parseDouble(price) < 0 || Double.parseDouble(priceDone) < 0){
            throw  new NotAPositiveValueException();
        }
    }
    /**
     * Ловит все исключения.
     * @param e - NumberFormatException, NotAPositiveValueException, WrongAmpuntNeighboursException.
     */
    private static void catchException(Exception e) {
        new JOptionPane().showMessageDialog(null, e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
    }

}
