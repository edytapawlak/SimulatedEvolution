import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by szpirala on 18.05.17.
 */
public class Main extends PApplet {

    private PGraphics worldScreen;
    private GensPlot plot;
    private PopulationPlot popCountPlot;
    private World world;
    private int width = 640;
    private int height = 480;

    private float zoom = 2;
    private boolean isZoomPressed = false;
    private boolean isUnzoomPressed = false;
    int day;
    int lastTimeCheck;
    int timeIntervalFlag = 4 * (int)frameRate;

    public void settings() {
        size(width, height, P3D);
        world = new World(this, width/2, height/2);
        plot = new GensPlot(world, 100, 100);
        popCountPlot = new PopulationPlot(world, this, 100, 50, 100);
        day = 0;
        world.dayN(day);

    }

    public void setup() {
        worldScreen = createGraphics(width-120, height, P3D);
        noStroke();
//        colorMode(RGB, height, height, height);
        lastTimeCheck = millis();
    }


    public void draw() {
        if (isUnzoomPressed && zoom > 0.5) {
            zoom -= 0.01f;
        }
        if (isZoomPressed && zoom < 2.9) {
            zoom += 0.01f;
        }

        world.worldDay();
        worldScreen.noStroke();
        worldScreen.beginDraw();
            worldScreen.background(205, 176, 130);
            world.drawPlants(worldScreen);
            world.drawAnimals(worldScreen);
            worldScreen.camera(
                    (mouseX + worldScreen.width/2.0f) / 2.0f,
                    (mouseY + worldScreen.height/2.0f) / 2.0f,
                    (worldScreen.height / (float)Math.exp(this.zoom)) / (float)Math.tan(Math.PI * 30.0 / 180.0),
//                    (worldScreen.height / (float)Math.exp(this.zoom)) / zoom,
                    (mouseX + worldScreen.width/2.0f) / 2.0f,
                    (mouseY + worldScreen.height/2.0f) / 2.0f,
                    0.0f,
                    0.0f,
                    1.0f,
                    0.0f);
            worldScreen.translate(mouseX, mouseY, 0.5f);
//            worldScreen.rotateX(-PI/6);
//            worldScreen.rotateY(PI/3);
            worldScreen.fill(255, 0, 0);
            worldScreen.sphere(10f);
        worldScreen.endDraw();
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
        }
        image(worldScreen, 0, 0);
    }

    public void mouseClicked() {
        if (mouseButton == LEFT) {
            world.addJungle(mouseX/2, mouseY/2);
        } else if (mouseButton == RIGHT) {
            world.addAnimal(mouseX/2, mouseY/2);
        }
    }

    public void keyPressed() {
        if (key == '=' || key == '+') {
            isUnzoomPressed = false;
            isZoomPressed = true;
        }
        if (key == '-' || key == '_') {
            isZoomPressed = false;
            isUnzoomPressed = true;
        }
    }

    public void keyReleased() {
        if (key == '=' || key == '+') {
            isZoomPressed = false;
        }
        if (key == '-' || key == '_') {
            isUnzoomPressed = false;
        }
    }

    public static void main(String... args) {
        PApplet.main("Main");
    }
}
