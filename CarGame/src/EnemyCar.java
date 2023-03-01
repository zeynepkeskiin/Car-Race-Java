import java.awt.*;
import java.util.Random;

public class EnemyCar extends BaseCar {

  //  private int eCarX;
    public double eCarY = 0;

    Random random = new Random();


    public EnemyCar(int x, int y) {
        super(x, y);
        loadImage();
    }

    public void loadImage(){


        loadImage("src\\resources\\" + random.nextInt(5) + ".png");
        getImageDimensions();

    }

    public void move(){
        eCarY += 0.2;
        y += eCarY;
    }
}
