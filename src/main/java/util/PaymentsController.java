package util;

import javax.swing.*;
import java.awt.*;

/**
 * Контроллер главного окна
 *
 * @author Терентьев Евгений
 */
public class PaymentsController extends JFrame{

    JMenuBar menuBar;
    JMenu menu;

    public PaymentsController(){
        initComponents();


    }
    private void initComponents(){
        this.setLayout(new FlowLayout());
        menuBar = new JMenuBar();
        menu = new JMenu("Меню");
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

    }
}
