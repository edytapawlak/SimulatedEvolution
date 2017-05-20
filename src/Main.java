import processing.core.PApplet;

/**
 * Created by szpirala on 18.05.17.
 */
public class Main extends PApplet {

    private GensPlot plot;
    private PopulationPlot popCountPlot;
    private World world;
    private int width = 640;
    private int height = 480;
    int day;
    int lastTimeCheck;
    int timeIntervalFlag = 4 * (int)frameRate;

    public void settings() {
        size(width, height);
        world = new World(this, width - 120, height);
        plot = new GensPlot(world, 100, 100);
        popCountPlot = new PopulationPlot(world, this, 100, 50, 100);
        day = 0;
        world.dayN(day);

    }

    public void setup() {
        noStroke();
//        colorMode(RGB, height, height, height);
        lastTimeCheck = millis();
    }


    public void draw() {
        background(0);
        world.worldDay();
        world.drawPlants();
        world.drawAnimals();
        fill(190);
        rect(width - 120, 0, 120, height);
        fill(5);
        text("Iteration: " + day, width - 115, 30);
        int avarage[] = world.avarageLifeTime();
        text("Avg. age: \t" + avarage[0] + "s, \t" + avarage[1], width - 115, 60);
        plot.drawPlot(width - 115, height - 50);
        day++;
        popCountPlot.drawPlot(width - 115, height - 200);
        if (millis() > lastTimeCheck + timeIntervalFlag) {
            lastTimeCheck = millis();
            popCountPlot.updatePlot();
            plot.updateGensPop();
//            println( "something awesome happens here" );
        }
    }

    public void mouseClicked() {
        if (mouseButton == LEFT) {
            world.addJungle(mouseX, mouseY);
        } else if (mouseButton == RIGHT) {
            world.addAnimal(mouseX, mouseY);
        }
    }

    public static void main(String... args) {
        PApplet.main("Main");
    }
}
