/*
Trains:
Приложение, моделирующее информационную систему на железнодорожном вокзале

Main:
Точка входа в приложение

Терентьев Евгений, ИВТ-42БО
 */

import ui.Menu;
import ui.PaymentsController;

import javax.swing.*;

public class Main {
    /**
     * Точка входа в приложение
     * @param args аргументы
     */
    public static void main(final String[] args) {
        JFrame mainFrame = new JFrame("Payments");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new Menu());
        //mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setContentPane(new PaymentsController());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
