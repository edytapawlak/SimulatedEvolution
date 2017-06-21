import java.util.Random;

public class Animal {

    private int x;
    private int y;
    private int age;
    private int childrens;
    private int energy;
    /**
     * 8 genów odpowiadających za kierunek
     */
    private int[] gens;
    private int kanibalGen;
    private int howManyChild;

    public Animal(int x, int y, int energy, int[] gens, int kanibalGen){
        this.gens = gens;
        this.x = x;
        this.y = y;
        this.age = 0;
        this.energy = energy;
        this.kanibalGen = kanibalGen;
        this.childrens = 0;
    }

    public Animal(int x, int y, int energy){
        Random rand = new Random();
        gens = new int[8];
        for (int i = 0; i < gens.length; i++) {
//            gens[i] = ThreadLocalRandom.current().nextInt(0, 10 + 1);
            gens[i] = rand.nextInt(11);
        }
        this.kanibalGen = rand.nextInt(11);
        this.x = x;
        this.y = y;
        this.age = 0;
        this.energy = energy;
        this.childrens = 0;
    }


    public void eat(int energy){
        this.energy += energy;
    }

    public Animal reproduce(){
        int[] chldrenGenes;
        if (energy > 200) {
            chldrenGenes = new int[8];
            for (int i = 0; i < this.gens.length; i++) {
                chldrenGenes[i] = this.gens[i];
            }
            Random rand = new Random();
//            int randomIndex = ThreadLocalRandom.current().nextInt(0, 8);
//            int random = ThreadLocalRandom.current().nextInt(0, 3);
            int randomIndex = rand.nextInt(8);
            int random = rand.nextInt(3);
            chldrenGenes[randomIndex] = Math.max(1, this.gens[randomIndex] + random - 1);
            int childCanibalGen = Math.max(this.kanibalGen + rand.nextInt(3) - 1, 0);
            this.energy = (int) (this.energy / 2);
            this.childrens++;
            return new Animal(this.getX(), this.getY(), this.energy, chldrenGenes, childCanibalGen);
        }
        return null;
    }

    public void bit(Animal animal){
        if(this.energy < 30) {
            int bitedEnergy = Math.min(animal.getEnergy(), kanibalGen * 10);
            this.energy += bitedEnergy;
            animal.decreaseEnergy(bitedEnergy);
        }
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
        Random rand = new Random();
        int random = rand.nextInt(sum + 1);
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
        this.y = (this.y - 1) % height;
    }

    private void moveES(int width, int height){
        this.x = (this.x + 1) % width;
        this.y = (this.y + 1) % height;
    }

    private void moveWS(int width, int height){
        this.x = (this.x - 1) % width;
        this.y = (this.y + 1) % height;
    }

    private void moveWN(int width, int height){
        this.x = (this.x - 1) % width;
        this.y = (this.y - 1) % height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void decreaseEnergy(int value){
        energy -= value;
    }

    public int getEnergy() {
        return energy;
    }

    public int[] getGens() {
        return gens;
    }

    public int getCanibal() {
        return kanibalGen;
    }

    public int getChildrens() {
        return childrens;
    }
}
