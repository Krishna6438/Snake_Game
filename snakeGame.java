import java.util.*;

import javax.swing.JFrame;

public class snakeGame extends JFrame {
    snakeGame() { // constructor
        // setLocation(100,100); // location of frame

        super("Snake game"); // title

        add(new Board()); // calling another class and add to frame
        pack(); // for reload to refresh the frame so that changes can be visible in frame

        // setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null); // locate to centre
        // automatically create frame
    }

    public static void main(String[] args) {
        new snakeGame(). setVisible(true);; // anonymous object
    }
}