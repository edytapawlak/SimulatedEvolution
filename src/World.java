import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by szpirala on 20.05.17.
 */
public class World {

    private PApplet context;
    private int width;
    private int height;
    private int jungleRadius = 50;

    private List<Jungle> jungles;
    private List<Plant> plants;
    private List<Animal> animals;

    public World(PApplet context, int width, int height) {
        plants = new ArrayList<>();
        jungles = new ArrayList<>();
        animals = new ArrayList<>();

        //testowa dżungla
        jungles.add(new Jungle((int) (width / 2), (int) (height / 2), jungleRadius));
        // testowe zwierze
        animals.add(new Animal((int) (width / 2), (int) (height / 2), 10000));
        this.context = context;
        this.width = width;
        this.height = height;
    }

    public void worldDay() {
//        this.drawPlot();
//        this.drawPlants();
        this.addPlants();
//        this.drawAnimals();
        this.moveAnimals();
    }

    public void dayN(int n) {
        int i = 0;
        while (i < n) {
            addPlants();
            moveAnimals();
            i++;
        }
    }

    /**
     * @param left   lewy x lewego górnego rogu obszaru losowania
     * @param top    górny y lewego górnego rogu obszaru losowania
     * @param width  szerokość obszatu z którego losuje
     * @param height wysokość obszaru z którego losuje
     */
    private Plant[] randomPlant(int left, int top, int width, int height) {
        int randomX;
        int randomY;
        Plant[] pl = new Plant[2];
        Random rand = new Random();
        for (int i = 0; i < pl.length; i++) {
//            randomX = ThreadLocalRandom.current().nextInt(left, width + 1);
//            randomY = ThreadLocalRandom.current().nextInt(top, height + 1);
            randomX = rand.nextInt(width + 1);
            randomY = rand.nextInt(height + 1);
            pl[i] = new Plant(randomX, randomY);
        }
        return pl;
    }

    public void addPlants() {
        Plant[] pl = randomPlant(0, 0, width, height);
        for (int i = 0; i < pl.length ; i++) {
            plants.add(pl[i]);
        }
//        plants.add(randomPlant(0, 0, width, height));
        for (Jungle j :
                jungles) {
            for (int i = 0; i < 2; i++) {
                plants.add(j.randomPlant());
            }
        }
    }

    public void addJungle(int x, int y) {
        jungles.add(new Jungle(x, y, jungleRadius));
    }

    public void addAnimal(int x, int y) {
        animals.add(new Animal(x, y, 1000));
    }

    public void moveAnimals() {
        Animal child;
        List<Animal> childsToAdd = new LinkedList<>();
        List<Animal> deadAnimals = new LinkedList<>();
        for (Animal a :
                animals) {
            a.decreaseEnergy(1);
            if (a.getEnergy() < 0) {
                deadAnimals.add(a);
            } else {
                a.move(width, height);
                Plant plant = getPlantByCoords(a.getX(), a.getY());
                if (plant != null) {
                    a.eat(plant.getEnnergy());
                    plants.remove(plant);
                }

                Animal animal = getAnimalByCoords(a.getX(), a.getY());
                if (animal != null) {
                    a.bit(animal);
//                    System.out.println("BIT!");
                }

                child = a.reproduce();
                if (child != null) {
                    childsToAdd.add(child);
                }
            }
        }
        animals.removeAll(deadAnimals);
        animals.addAll(childsToAdd);
    }


    public Plant getPlantByCoords(int x, int y) {
        for (Plant p :
                plants) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }

    public Animal getAnimalByCoords(int x, int y) {
        for (Animal animal :
                animals) {
            if (animal.getX() == x && animal.getY() == y) {
                return animal;
            }
        }
        return null;
    }


    public void drawPlants() {
        for (Plant p :
                plants) {
            context.fill(0, 255, 0);
            context.rect(p.getX(), p.getY(), p.getHeight(), p.getHeight());
        }
    }

    public void drawAnimals() {
        for (Animal a :
                animals) {
            context.fill(a.getEnergy(), 0, 0);
            context.rect(a.getX(), a.getY(), 1, 1);
        }
    }

    public float[] getGensData() {
        float[] sum = new float[8];
        int[] gens;
        Animal a;
        for (int i = 0; i < animals.size(); i++) {
            a = animals.get(i);
            gens = a.getGens();
            for (int j = 0; j < gens.length; j++) {
                sum[j] += gens[j];
            }
        }
        if (animals.size() > 0) {
            for (int i = 0; i < sum.length; i++) {
                sum[i] = 10 * (sum[i] / animals.size());
            }
        }
        return sum;
    }

    public int[] avarageLifeTime(){
        int[] sum = new int[2];
        for (int i = 0; i < animals.size(); i++) {
            sum[1] += animals.get(i).getEnergy();
        }
        float fps = context.frameRate;
        sum[0] = (fps > 0 && animals.size() > 0) ? (int)((sum[1]/animals.size())/fps) : 0;
        sum[1] = (animals.size() > 0) ? sum[1] / animals.size(): 0;
        return sum;
    }

    public int getCount(){
        return animals.size();
    }

    public PApplet getContext() {
        return context;
    }
}
