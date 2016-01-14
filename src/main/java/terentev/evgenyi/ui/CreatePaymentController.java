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
 * @autor Терентьев Евгений
 */
public class CreatePaymentController extends JFrame{
    /**
     * Поля для заполнения.
     */
    private JTextField fioTextField;
    private JTextField priceTextField;
    private JTextField priceDoneTextField;
    /**
     * Выбор типа платежа.
     */
    private JComboBox<String> typeListComboBox;
    /**
     * Ссылка на главное окно.
     */
    private PaymentsController mainWindow;
    /**
     * Стандартные типы платежей.
     */
    private String[] defaultTypes = {"Квартплата", "Плата за электроэнергию", "Плата за телефон"};

    /**
     * Конструктор.
     * @param mainWindow сслка на главное окно
     */
    public CreatePaymentController(PaymentsController mainWindow) {
        this.mainWindow = mainWindow;
        getContentPane().setLayout(new GridLayout(5, 1));
        initComponents();
        setPreferredSize(new Dimension(350, 280));
        setResizable(false);
    }

    /**
     * Добавление компонентов.
     */
    private void initComponents() {
        setTitle("Добавление платежа");
        initFioLayout();
        initPriceLayout();
        initPriceDefaultLayout();
        initTypeLayout();
        initButtonLayout();
        pack();
    }

    /**
     * Инициализация поля с вводом ФИО.
     */
    private void initFioLayout() {
        JPanel fioPanel = new JPanel();
        fioTextField = new JTextField();
        fioTextField.setPreferredSize(new Dimension(200, 24));
        fioPanel.add(new JLabel("Ф.И.О."));
        fioPanel.add(fioTextField);
        getContentPane().add(fioPanel);
    }

    /**
     * Инициализация поля с требуемой суммой.
     */
    private void initPriceLayout() {
        JPanel pricePanel = new JPanel();
        priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(200, 24));
        pricePanel.add(new JLabel("Сумма платежа"));
        pricePanel.add(priceTextField);
        getContentPane().add(pricePanel);
    }

    /**
     * Инициализация поля внесенной суммы.
     */
    private void initPriceDefaultLayout() {
        JPanel priceDonePanel = new JPanel();
        priceDoneTextField = new JTextField();
        priceDoneTextField.setPreferredSize(new Dimension(200, 24));
        priceDonePanel.add(new JLabel("Внесенная сумма"));
        priceDonePanel.add(priceDoneTextField);
        getContentPane().add(priceDonePanel);
    }

    /**
     * Инициализация выбора вида платежа.
     */
    private void initTypeLayout() {
        JPanel typePanel = new JPanel();
        typeListComboBox = new JComboBox<>(defaultTypes);
        typePanel.add(new JLabel("Вид платежа"));
        typePanel.add(typeListComboBox);
        getContentPane().add(typePanel);
    }

    /**
     * Инициализация кнопок.
     */
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

    /**
     * Добавление нового счета в базу.
     */
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

    /**
     * Проверка полей на корректность.
     * @param price выставленная цена
     * @param priceDone оплачено
     * @throws Exception
     */
    private void checkFieldCorrect(String price, String priceDone) throws Exception{
        if(fioTextField.getText().isEmpty())
            throw new EmptyFieldException();
        if(Double.parseDouble(price) < Double.parseDouble(priceDone))
            throw new PayOverPriceException();
        if(Double.parseDouble(price) < 0 || Double.parseDouble(priceDone) < 0)
            throw  new NotAPositiveValueException();
    }
    /**
     * Ловит все исключения.
     * @param e - NumberFormatException, NotAPositiveValueException, WrongAmpuntNeighboursException.
     */
    private static void catchException(Exception e) {
        new JOptionPane().showMessageDialog(null, e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
    }

}
