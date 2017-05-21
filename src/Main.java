import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Main extends PApplet {

    private PGraphics worldScreen;
    private PImage filterTexture;
    private GensPlot plot;
    private PopulationPlot popCountPlot;
    private World world;
    private int WORLD_SIZE = 400;
    private int width = 640;
    private int height = 480;

    private int xCoord;
    private int yCoord;
    private float xScale;
    private float yScale;

    private float zoom = 1;
    private boolean isZoomPressed = false;
    private boolean isUnzoomPressed = false;
    int day;
    int lastTimeCheck;
    int timeIntervalFlag = 4 * (int)frameRate;

    public void settings() {
        size(width, height, P3D);
        world = new World(this, WORLD_SIZE, WORLD_SIZE);
        plot = new GensPlot(world, 100, 100);
        popCountPlot = new PopulationPlot(world, this, 100, 50, 100);
        day = 1000;
        world.dayN(day);
    }

    public void setup() {
        worldScreen = createGraphics(width-120, height, P3D);
        filterTexture = loadImage("filter.png");
        filterTexture.resize(worldScreen.width, worldScreen.height);
        noStroke();
        lastTimeCheck = millis();

        xScale = Math.max((float)width / (float)WORLD_SIZE, 1.0f);

//        yScale = (float) Math.max(
//                Math.ceil(
//                        (float)height / (float)WORLD_SIZE), 1.0f);
        yScale = xScale;
    }


    public void draw() {
        noCursor();
        xCoord = (int) ((float)WORLD_SIZE * xScale * ((float)mouseX / (float)(width - 120)));
        yCoord = (int) ((float)WORLD_SIZE * yScale * ((float)mouseY / (float)height));

        if (isUnzoomPressed && zoom > 0) {
            zoom -= 0.01f;
        }
        if (isZoomPressed && zoom < 2.9) {
            zoom += 0.01f;
        }

        world.worldDay();
        worldScreen.noStroke();
        worldScreen.beginDraw();
            worldScreen.background(73,56,49);
            worldScreen.fill(235,194,136);
            worldScreen.rect(0, 0, (WORLD_SIZE + 1) * xScale, (WORLD_SIZE + 1) * yScale);

            world.drawPlants(worldScreen);
            world.drawAnimals(worldScreen);

            worldScreen.camera(
                    xCoord,
                    yCoord,
                    (WORLD_SIZE / (float)Math.exp(this.zoom * xScale)),
                    xCoord,
                    yCoord,
                    0.0f,
                    0.0f,
                    1.0f,
                    0.0f);
            worldScreen.translate(xCoord, yCoord, 0.0f);
            worldScreen.fill(255, 117, 213, 200);
            worldScreen.box(8f/(this.zoom+1));
        worldScreen.endDraw();
        fill(190);
        rect(width - 120, 0, 120, height);
        fill(5);
        text("Iteration: " + day, width - 115, 30);
        text("Avg. age: " + world.avarageLifeTime() +
                "s,\nAvg. energy: " + world.avarageEnergy() , width - 115, 60);
        plot.drawPlot(width - 115, height - 50);
        day++;
        popCountPlot.drawPlot(width - 115, height - 200);
        if (millis() > lastTimeCheck + timeIntervalFlag) {
            lastTimeCheck = millis();
            popCountPlot.updatePlot();
            plot.updateGensPop();
        }
        image(worldScreen, 0, 0);
//        image(filterTexture, 0, 0);
    }

    public void mouseClicked() {
        if (mouseButton == LEFT) {
            world.addJungle((int) ((float)WORLD_SIZE * ((float)mouseX / (float)(width - 120))),
                    (int) ((float)WORLD_SIZE * ((float)mouseY / (float)height)));
        }
        if (mouseButton == RIGHT) {
            world.addAnimal((int) ((float)WORLD_SIZE * ((float)mouseX / (float)(width - 120))),
                    (int) ((float)WORLD_SIZE * ((float)mouseY / (float)height)));
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
