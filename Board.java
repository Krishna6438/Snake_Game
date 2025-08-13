

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private int dots;
    private final int All_Dots = 250000;
    private final int Dot_Size = 10;
    private final int Random_Position = 29;
    private int apple_x;
    private int apple_y;
    private final int[] x = new int[All_Dots];
    private final int[] y = new int[All_Dots];

    private Image apple;
    private Image dot;
    private Image head;

    private Timer timer;

    private boolean left_Direction = false;
    private boolean right_Direction = true;
    private boolean up_Direction = false;
    private boolean down_Direction = false;

    private boolean inGame = true;

    public Board() {
        addKeyListener(new TAdapter()); // keylistner is the method of actionlistner
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(300,300));
        loadImages();
        initGame();
    }

    

    private void loadImages() {
        ImageIcon i1 = new ImageIcon(getClass().getResource("icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(getClass().getResource("icons/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(getClass().getResource("icons/head.png"));
        head = i3.getImage();
    }

    private void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * Dot_Size;
        }
        locateApple();

        timer = new Timer(140, this);
        timer.start();
    }

    private void locateApple() {
        int r = (int) (Math.random() * Random_Position);
        apple_x = ((r * Dot_Size));
        r = (int) (Math.random() * Random_Position);
        apple_y = ((r * Dot_Size));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over";
        g.setColor(Color.white);
        g.drawString(msg, 150, 250);
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (left_Direction) {
            x[0] = x[0] - Dot_Size;
        }
        if (right_Direction) {
            x[0] = x[0] + Dot_Size;
        }
        if (up_Direction) {
            y[0] = y[0] - Dot_Size;
        }
        if (down_Direction) {
            y[0] = y[0] + Dot_Size;
        }

        // x[0]+=Dot_Size;
        // y[0]+=Dot_Size;
    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    public void checkCollison() {

        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (y[0] >= 500) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] >= 500) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (!inGame) {
            timer.stop();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollison();
            move();
        }
        repaint();
    }

    public class TAdapter extends KeyAdapter { // for keys
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!right_Direction)) {
                left_Direction = true;
                up_Direction = false;
                down_Direction = false;
            }

            if (key == KeyEvent.VK_RIGHT && (!left_Direction)) {
                right_Direction = true;
                up_Direction = false;
                down_Direction = false;
            }

            if (key == KeyEvent.VK_UP && (!down_Direction)) {
                up_Direction = true;
                right_Direction = false;
                left_Direction = false;
            }
            if (key == KeyEvent.VK_DOWN && (!up_Direction)) {
                down_Direction = true;
                right_Direction = false;
                left_Direction = false;
            }
        }
    }
}
