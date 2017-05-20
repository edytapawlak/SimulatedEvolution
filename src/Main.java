import processing.core.PApplet;

/**
 * Created by szpirala on 18.05.17.
 */
public class Main extends PApplet{

    private GensPlot plot;
    private World world;
    private int width = 640;
    private int height = 480;
    // Przykładowe dane
    private int[] gens1 = {10, 20, 30, 40, 50, 60, 70, 80, 90};
    private int[] gens2 = {90, 80, 70, 60, 50, 40, 30, 20, 10};

    public void settings(){
        size(width, height);
//        plot = new GensPlot(this, 100, 200);
        world = new World(this, width, height);
    }

    public void setup() {
        noStroke();
        colorMode(RGB, height, height, height);
    }


    public void draw(){
        background(250);

//        if(random(100) > 50){
//            plot.setGensPopulation(gens1);
//        } else {
//            plot.setGensPopulation(gens2);
//        }
//        plot.drawPlot(100, 500);
        world.drawPlants();
        world.addPlants();
        world.drawAnimals();
        world.moveAnimals();

    }

    public void mouseClicked(){
        world.addJungle(mouseX, mouseY);
    }

    public static void main(String... args){
        PApplet.main("Main");
    }
}
