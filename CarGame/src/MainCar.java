import java.awt.event.KeyEvent;
import java.util.Timer;

public class MainCar extends BaseCar {

    private int dirX = 0;
    private int dirY = 0;

    public MainCar(int x, int y) {
        super(x, y);
        loadImage();
    }

    public void loadImage() {

        loadImage("src//resources//main.png");
        getImageDimensions();

    }

    public void KeyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dirX -= 5;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dirX += 5;
        }
        if (key == KeyEvent.VK_UP) {
            dirY -= 5;
        }
        if (key == KeyEvent.VK_DOWN) {
            dirY += 5;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dirX = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dirX = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dirY = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dirY = 0;
        }

    }

    public void move() {

        x += dirX;
        y += dirY;

        if (y < 1) {
            y = 1;
        }
        if(y>425){
            y = 425;
        }
        if (x<90){
            x =90;
        }
        if (x>407){
            x=409;
        }

    }


}
