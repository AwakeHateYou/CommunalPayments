import javax.swing.*;

/**
 * Меню с действиями.
 */
public class Menu extends JMenu {
    JMenuItem addPayment;
    public Menu() {
        initComponents();
        add(addPayment);
        setVisible(true);

    }

    private void initComponents() {
        this.setText("Меню");
        addPayment = new JMenuItem("Добавить платеж");
        bind();
    }
    private void bind(){
        addPayment.addActionListener(e -> {
            CreatePaymentController window = new CreatePaymentController();
            //window.setTrainListModel(trainsListModel);
            window.pack();
            window.setVisible(true);
        });
    }
}
