import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oyun extends JPanel implements ActionListener {

    private double yolCizgiY = -160;

    private BufferedImage imageYol;
    private Timer timer;
    Coin coin;
    private int carX = 160;
    private int carY = 400;
    public int cani = 3;

    boolean puantamamladi = false;
    boolean gameEnd;
    int level = 0;
    //   private int yolY = 0;
    public int bekle = 2000;
    public int bekle2= 15000;
    public int score = 0;
    public int dscore = 2; //değişim miktarı

    int gecen_sure = 0;
    private MainCar mainCar;
    private EnemyCar enemyCar;


    TextOperation t1 ;

    Random random = new Random();
    //int[] carYol = {160,180,200,220,240,300,320,330,360,375};

    public Oyun() {
        initOyun();

        try {
            t1 = new TextOperation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initOyun() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        coin = new Coin((int)(Math.random()*200+160),random.nextInt(600));

        try {
            imageYol = ImageIO.read(new FileImageInputStream(new File("C:\\Users\\Zeynep\\Desktop\\araba\\yol.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainCar = new MainCar(carX, carY);
        enemyCar = new EnemyCar((int)(Math.random()*200+160), -100);
        //    carsList = new ArrayList<>();

        timer = new Timer(10, this);
        timer.start();
    }

    private void drawing(Graphics g) {

        //  gecen_sure += 5;

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(imageYol, 0, 0, 580, 700, this);

       /* g2d.drawImage(mainCar.getImage(), mainCar.getX(),
                mainCar.getY(), this);

        g2d.drawImage(enemyCar.getImage(), enemyCar.getX(),
                enemyCar.getY(), this);*/

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g2d.drawString("Puanınız: " + score,  10,550 );

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g2d.drawString("Kalan Can: " + cani,  10,530 );

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g2d.drawString("Level: " + level,  10,510 );


        // yol çizgisi oluştıurma

        g2d.fillRect(280,(int) yolCizgiY+160,15,80);
        yolCizgiY+= 0.8;
        g2d.fillRect(280,(int) yolCizgiY+320,15,80);

        g2d.fillRect(280,(int) yolCizgiY+480,15,80);

        g2d.fillRect(280,(int) yolCizgiY+640,15,80);
        // g2d.drawImage(coin.getCoinImage(),coin.getX(),coin.getY(),this);
        yolCizgiY+= 0.8;

        if (yolCizgiY>0)
            yolCizgiY=-160;

        g2d.drawImage(enemyCar.getImage(),enemyCar.getX(),enemyCar.getY(),this);
        g2d.drawImage(mainCar.getImage(),mainCar.getX(),mainCar.getY(),this);

        g2d.drawImage(coin.getCoinImage(),coin.getX(),coin.getY(),this);

        bekle2 -= 10;
        while (bekle2 < 0) {
            System.out.println("Level : " + level);
            bekle2 = 15000;
            level += 1;
            enemyCar.eCarY +=2;
        }
        bekle -= 10;
        while (bekle < 0) {
            System.out.println("score : " + score);
            bekle = 2000;
            score += dscore;

        }
        if (score > 50){
            gameEnd = true;
            puantamamladi= true;
        }


        oyunKontrol(g2d);

    }
    private boolean carpismaCheckPoint(){
        if (new Rectangle(coin.getX(),coin.getY(),coin.getWidth(),coin.getHeight()).intersects(mainCar.getX(),
                mainCar.getY(),mainCar.getWidth(),mainCar.getHeight())){
            return true;
        }
        return false;
    }
    public void defaultDegerler(){
        carX = 160;
        carY = 400;
        enemyCar.setX(160,0);
        enemyCar.eCarY = 0;

    }
    public void oyunKontrol(Graphics2D g2d) {
        if (gameEnd) {
            int width = 612;
            int height = 612;

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            if (puantamamladi == true){
                g2d.drawString("KAZANDINIZ", width / 2 - g2d.getFontMetrics().stringWidth("KAZANDINIZ") / 2, height / 2);
            }else {
                g2d.drawString("ÇARPTINIZ", width / 2 - g2d.getFontMetrics().stringWidth("ÇARPTINIZ") / 2, height / 2);

            }

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
            g2d.drawString("Puanınız: " + score, width / 2 - g2d.getFontMetrics().stringWidth("Puanınız:----") / 2, height / 2 + 55);
            timer.stop();



        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawing(g);
    }

    private void createEnemy() {

        if (enemyCar.getY() <= getHeight()) {
            enemyCar.move();
        } else if (enemyCar.getY() > getHeight()) {
            enemyCar.setVisible(false);
            enemyCar = new EnemyCar((int)(Math.random()*200+160), -100);

        }
    }

    private void aracYürü() {

        mainCar.move();
    }

    private boolean carpısmaKontrol() throws IOException {
        if (new Rectangle(mainCar.getX(), mainCar.getY(), mainCar.getWidth(), mainCar.getHeight())
                .intersects(enemyCar.getX(), enemyCar.getY(), enemyCar.getWidth(), enemyCar.getHeight())) {
            cani--;

            if (cani<1){

                t1.addText(score);

                //int[] a = t1.getText();

                gameEnd = true;
            }else {
                defaultDegerler();
            }

            return false;
        }
        gameEnd = false;
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(carpismaCheckPoint()){
            score +=10;
            coin = new Coin((int)(Math.random()*200+160),random.nextInt(600));
        }

        try {
            if (!carpısmaKontrol()) {

                // String mesaj = "Bitti";
                //JOptionPane.showMessageDialog(this, mesaj);

                //System.exit(0);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        aracYürü();
        createEnemy();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        int x = 0;



        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            mainCar.KeyPressed(e);

            if(x == 0 && key == KeyEvent.VK_SPACE){
                x=1;
                timer.stop();
            } else if (x==1 && key == KeyEvent.VK_SPACE) {
                x = 0;
                timer.start();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            mainCar.keyReleased(e);
        }
    }
}
