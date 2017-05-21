import processing.core.PApplet;
import processing.core.PGraphics;

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
    private int jungleRadius = 25;
    private float scale;

    private List<Jungle> jungles;
    private List<Plant> plants;
    private List<Animal> animals;

    public World(PApplet context, int width, int height) {
        plants = new ArrayList<>();
        jungles = new ArrayList<>();
        animals = new ArrayList<>();

        jungles.add(new Jungle(width/2, height/2, jungleRadius));
        animals.add(new Animal(width/2, height/2, 1000));

        this.context = context;
        this.width = width;
        this.height = height;
        this.scale = Math.max((float)context.width / (float)width, 1.0f);
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
     * @param width  szerokość obszatu z którego losuje
     * @param height wysokość obszaru z którego losuje
     */
    private Plant randomPlant(int width, int height) {
        int randomX;
        int randomY;
        Random rand = new Random();
        randomX = rand.nextInt(width + 1);
        randomY = rand.nextInt(height + 1);
        return new Plant(randomX, randomY);
    }

    public void addPlants() {
        float stableWorldSize = 300.0f;
        int max = (int) (((float)width/stableWorldSize)*((float)width/stableWorldSize));
        for (int i = 0; i <= max; i++) {
            plants.add(randomPlant(width, height));
        }
        for (Jungle j :
                jungles) {
                plants.add(j.randomPlant());
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


    public void drawPlants(PGraphics layer) {
        for (Plant p :
                plants) {
            layer.fill(0, 109, 37);
            layer.rect(p.getX()*scale, p.getY()*scale, p.getHeight()*scale, p.getHeight()*scale);
        }
    }

    public void drawAnimals(PGraphics layer) {
        for (Animal a :
                animals) {
            layer.fill(
                    Math.min(10*a.getCanibal(), 255.0f),
                    0, 0);
            layer.rect(a.getX()*scale, a.getY()*scale, scale, scale);
        }
    }

    public float[] getGensData() {
        float[] sum = new float[9];
        int[] gens;
        Animal a;
        for (int i = 0; i < animals.size(); i++) {
            a = animals.get(i);
            gens = a.getGens();
            for (int j = 0; j < gens.length; j++) {
                sum[j] += gens[j];
            }
            sum[sum.length - 1] += a.getCanibal();
        }
        if (animals.size() > 0) {
            for (int i = 0; i < sum.length; i++) {
                sum[i] = 10 * (sum[i] / animals.size());
            }
        }
        return sum;
    }


    public int avarageEnergy() {
        int sum = 0;
        for (Animal animal :
                animals) {
            sum += animal.getEnergy();
        }
        return (animals.size() > 0) ? sum / animals.size() : 0;
    }

    public String avarageLifeTime() {
        float avge = avarageEnergy();
        float fps = context.frameRate;
        float out = (fps > 0 && animals.size() > 0) ? avge / fps : 0.0f;
        return String.format("%.2g", out);
    }

    public int getCount(){
        return animals.size();
    }

    public PApplet getContext() {
        return context;
    }
}
