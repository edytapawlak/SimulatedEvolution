import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by szpirala on 20.05.17.
 */
public class Jungle {

    /**
     * Dżungla będzie kołem, częściej rosną w niej rośliny
     * x, y współrzędne środka koła
     *
     */
    private int x;
    private int y;
    private int radius;

    public Jungle(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Plant randomPlant(){
        int randomRarius = ThreadLocalRandom.current().nextInt(0, radius + 1);
        double randomAngle = Math.random() * 2 * Math.PI;

        int plantX = (int)(randomRarius * Math.cos(randomAngle)) + this.x;
        int plantY = (int)(randomRarius * Math.sin(randomAngle)) + this.y;

        return new Plant(plantX, plantY);

    }
}
