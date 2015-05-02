package com.uyutaka;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyAdapter;

/**
 * Created by Yutaka on 15/05/02.
 */
public class GameTestMain {
    JFrame frame1;
    BufferStrategy bstrategy;
    int count = 0; //run()が処理された回数

    int cy = 200;
    boolean isSpkey = false; //SPACEキーが押されているかどうか


    GameTestMain(){

        //引数はタイトル
        frame1 = new JFrame("Game Start!");


        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closeボタンの追加
        frame1.setBackground(Color.RED);                     //背景の色
        frame1.setResizable(false);                            //windowのサイズ変更不可
        frame1.setVisible(true);                               //window表示


        Insets insets = frame1.getInsets();
        frame1.setSize(600 + insets.left + insets.right, 400 + insets.top + insets.bottom);
        frame1.setLocationRelativeTo(null);   //windowを中央に配置

        frame1.setIgnoreRepaint(true); //JFrame標準の画面書き換えを無効化
        frame1.createBufferStrategy(2);
        bstrategy = frame1.getBufferStrategy();


        frame1.addKeyListener(new MyKeyAdapter());

        Timer t = new Timer();
        t.schedule(new MyTimerTask(), 10, 500);//引数２:遅延時間　引数３:実行間隔

    }
    public static void main(String [] args){
        GameTestMain gtm = new GameTestMain();
    }

    class MyTimerTask extends TimerTask{


        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {

            Graphics g = bstrategy.getDrawGraphics();
            if(bstrategy.contentsLost() == false){    //bufferStrategy利用可能の時

                //タイトルバーを考慮して原点修正
                Insets insets = frame1.getInsets();
                g.translate(insets.left, insets.top);

                //正方形の描写
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, 100, 100);

                g.setFont(new Font("Self", Font.PLAIN, 40));
                drawStringCenter("hello", 100, g);   //独自メソッドで文字をセンターに




                if(isSpkey == true){
                    cy = cy - 10;
                }else{
                    cy = cy + 10;
                }
                g.clearRect(0, 0, 600, 400);
                g.setColor(Color.BLUE);
                g.fillOval(250, cy, 100, 100);

                bstrategy.show();
                g.dispose();                          //インスタンスの破棄
            }
            count = count + 1;
            System.out.println(count);
        }


        void drawStringCenter(String str, int y, Graphics g){
            int fw = frame1.getWidth() / 2;
            FontMetrics fm = g.getFontMetrics();
            int strw = fm.stringWidth(str) / 2;
            g.drawString(str, fw-strw, y);
        }
    }


    class MyKeyAdapter extends KeyAdapter{



        /**
         * Invoked when a key has been pressed.
         *
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                System.out.println("Pressed SPACE");
                isSpkey = true;
            }
        }

        /**
         * Invoked when a key has been released.
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                System.out.println("Released SPACE");
                isSpkey = false;
            }
        }
    }
}
