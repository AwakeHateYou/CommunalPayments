import javax.swing.*;
import java.awt.*;

/**
 * Created by etere on 07.01.2016.
 */
public class PaymentsController extends JPanel {
    private JScrollPane scrollPayments;
    private JList<String> listPayments;
    public PaymentsController(){
        initialize();
        add(scrollPayments);
        setPreferredSize(new Dimension(300,300));
       // setSize(300, 300);
    }
    private void initialize(){
        scrollPayments = new JScrollPane();
        scrollPayments.add(new JLabel("Test"));
    }
}
