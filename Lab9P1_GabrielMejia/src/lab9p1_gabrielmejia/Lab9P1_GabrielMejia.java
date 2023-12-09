
package lab9p1_gabrielmejia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lab9P1_GabrielMejia extends JFrame {
    public Lab9P1_GabrielMejia() {
        setTitle("Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem snakeGameItem = new JMenuItem("Run Snake Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        snakeGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Run Snake Game
                try {
                    // Replace "com.yourpackage" with the actual package name
                    Class<?> snakeClass = Class.forName("Gusanito.Gusanito");
                    java.lang.reflect.Constructor<?> constructor = snakeClass.getConstructor(int.class, int.class);
                    constructor.newInstance(8, 8); // You can set default dimensions here
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit program
                System.exit(0);
            }
        });

        gameMenu.add(snakeGameItem);
        gameMenu.add(exitItem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lab9P1_GabrielMejia().setVisible(true);
            }
        });
    }
}
