/**
 * Created by szpirala on 18.05.17.
 */
public class Plant {
    private int x;
    private int y;
    private int energy;
    private int height;

    public Plant(int x, int y){
        this.x = x;
        this.y = y;
        this.energy = 60;
        this.height = 3;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEnnergy() {
        return energy;
    }

    public int getHeight(){
        return height;
    }

}
