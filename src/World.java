import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        animals = new LinkedList<>();
        animals.add(new Animal((int) (width / 2), (int) (height / 2)));
        //testowa dżungla
        jungles.add(new Jungle(100, 100, jungleRadius));
        this.context = context;
        this.width = width;
        this.height = height;
    }

    /**
     * @param left   lewy x lewego górnego rogu obszaru losowania
     * @param top    górny y lewego górnego rogu obszaru losowania
     * @param width  szerokość obszatu z którego losuje
     * @param height wysokość obszaru z którego losuje
     */
    private Plant randomPlant(int left, int top, int width, int height) {
        int randomX = ThreadLocalRandom.current().nextInt(left, width + 1);
        int randomY = ThreadLocalRandom.current().nextInt(top, height + 1);

        Plant pl = new Plant(randomX, randomY);
        return pl;
    }

    public void addPlants() {
        plants.add(randomPlant(0, 0, width, height));
        for (Jungle j :
                jungles) {
            plants.add(j.randomPlant());
        }
    }

    public void addJungle(int x, int y) {
        jungles.add(new Jungle(x, y, jungleRadius));
    }

    public void moveAnimals() {
        for (Animal a :
                animals) {
            a.move(width, height);
            Plant plant = getPlantByCoords(a.getX(), a.getY());
            if (plant != null) {
                a.eat(plant.getEnnergy());
                plants.remove(plant);
            }
        }
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
            context.fill(255, 0, 0);
            context.rect(a.getX(), a.getY(), 5, 5);
        }
    }
}
