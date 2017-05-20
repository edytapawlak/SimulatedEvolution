import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by szpirala on 18.05.17.
 */
public class Animal {

    private int x;
    private int y;
    private int direction;
    private int age;
    private Animal[] chilrens;
    private int energy = 100;
    /**
     * 8 genów odpowiadających za kierunek i szybkość?
     */
    private int[] gens;
    private int howManyChild;

    public Animal(int[] gens){
        this.gens = gens;
    }

    public Animal(int x, int y){
        gens = new int[8];
        for (int i = 0; i < gens.length; i++) {
            gens[i] = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        }
        this.x = x;
        this.y = y;
    }

    public void eat(int energy){
        this.energy += energy;
        System.out.println(this.energy);
    }

    /**
     * A -- animal
     *  -------------
     *  | 0 | 1 | 2 |
     *  -------------
     *  | 7 | A | 3 |
     *  -------------
     *  | 6 | 5 | 4 |
     *  -------------
     */
    public void move(int width, int height){
        int sum = 0;
        for (int g :
                gens) {
            sum += g;
        }
        int random = ThreadLocalRandom.current().nextInt(0, sum + 1);
        int partSum = 0;
        int i = 0;
        while (partSum < random){
            partSum += gens[i];
            i++;
        }
        switch (i) {
            case 0:
                moveWN(width, height);
                break;
            case 1:
                moveUp(width, height);
                break;
            case 2:
                moveEN(width, height);
                break;
            case 3:
                moveRight(width, height);
                break;
            case 4:
                moveES(width, height);
                break;
            case 5:
                moveDown(width, height);
                break;
            case 6:
                moveWS(width, height);
                break;
            case 7:
                moveLeft(width, height);
                break;
        }
    }

    private void moveLeft(int width, int height){
        this.x = (this.x - 1);
        if(this.x < 0 ){
            this.x = width;
        }
    }

    private void moveRight(int width, int height){
        this.x = (this.x + 1) % width ;
    }

    private void moveUp(int width, int height){
        this.y = (this.y - 1);
        if(this.y < 0){
            this.y = height;
        }
    }

    private void moveDown(int width, int height){
        this.y = (this.y + 1) % height;
    }

    private void moveEN(int width, int height){
        this.x = (this.x + 1) % width;
        this.y = (this.y +1) % height;
    }

    private void moveES(int width, int height){
        this.x = (this.x + 1) % width;
        this.y = (this.y - 1) % height;
    }

    private void moveWS(int width, int height){
        this.x = (this.x - 1) % width;
        this.y = (this.y - 1) % height;
    }

    private void moveWN(int width, int height){
        this.x = (this.x - 1) % width;
        this.y = (this.y + 1) % height;
    }

    public void reproduce(){
        this.chilrens = new Animal[gens.length - 1];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
