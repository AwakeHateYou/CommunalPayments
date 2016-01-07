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

    }
}
