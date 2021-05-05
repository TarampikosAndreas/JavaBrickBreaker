import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Board extends JPanel implements KeyListener,ActionListener {


    private boolean play = false;
    private int score = 0;
    private int totalBricks = 24;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPossX = 120;
    private int ballPossY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;
    private GameGen map;


//    private Vector<BarCell> bar;
    //private int direction;

    public Board(){
        map = new GameGen(3, 8);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }

//    public void initBar(){
//        bar = new Vector<>();
//        bar.add(new BarCell(440,550));
//        bar.add(new BarCell(450,550));
//        bar.add(new BarCell(460,550));
//        bar.add(new BarCell(430,550).setSurface());
//        bar.add(new BarCell(470,550));
//        bar.add(new BarCell(480,550).setSurface());
//        repaint();
//
//    }

    @Override
    protected void paintComponent(Graphics g) {g.setColor(Color.black);
        g.setColor(Color.CYAN);
        g.fillRect(1, 1, 900, 600);

        map.draw((Graphics2D) g);

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 3, 600);
        g.fillRect(0, 0, 900, 3);
        g.fillRect(881, 0, 3, 600);

        g.setColor(Color.BLACK);
        g.setFont(new Font("calibre", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        g.setColor(Color.BLACK);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.BLUE);
        g.fillOval(ballPossX, ballPossY, 20, 20);

        if (ballPossY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        if(totalBricks == 0){
            play = false;
            ballDirY = -2;
            ballDirX = -1;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);


        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballPossX, ballPossY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballPossX, ballPossY, 20, 20);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            if (ballPossX + 19 <= brickrect.x || ballPossX + 1 >= brickrect.x + bricksWidth) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }
                    }


                }
            }


            ballPossX += ballDirX;
            ballPossY += ballDirY;
            if (ballPossX < 0) {
                ballDirX = -ballDirX;
            }
            if (ballPossY < 0) {
                ballDirY = -ballDirY;
            }
            if (ballPossX > 670) {
                ballDirX = -ballDirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballPossX = 120;
                ballPossY = 350;
                ballDirX = -1;
                ballDirY = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map = new GameGen(3, 7);

                repaint();
            }
        }


    }

    public void moveRight ()
    {
        play = true;
        playerX += 20;
    }
    public void moveLeft ()
    {
        play = true;
        playerX -= 20;
    }

}

