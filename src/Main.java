import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

public class Main extends PApplet {

    private PGraphics worldScreen;
    private PImage filterTexture;
    private PFont font;

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

    private static int statBarWidth = 130;
    private static int statBarBoarderWidth = 10;
    private static int statLeftPadding = 11;

    public void settings() {
        size(width, height, P3D);
        world = new World(this, WORLD_SIZE, WORLD_SIZE);
        plot = new GensPlot(world, 100, 100);
        popCountPlot = new PopulationPlot(world, this, 100, 50, 100);
        day = 0;
        world.dayN(day);
    }

    public void setup() {
        font = createFont("Roboto-Regular.ttf", 12);
        worldScreen = createGraphics(width-statBarWidth, height, P3D);
        filterTexture = loadImage("filter.png");
        filterTexture.resize(worldScreen.width, worldScreen.height);
        noStroke();
        lastTimeCheck = millis();

        xScale = Math.max((float)width / (float)WORLD_SIZE, 1.0f);

//        yScale = (float) Math.max(
//                Math.ceil(
//                        (float)height / (float)WORLD_SIZE), 1.0f);
        yScale = xScale;
        noCursor();
    }


    public void draw() {
        textFont(font);
        xCoord = (int) ((float)WORLD_SIZE * xScale * ((float)mouseX / (float)(width - statBarWidth)));
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
            worldScreen.background(82, 70, 86);
            worldScreen.fill(235,194,136);
            worldScreen.rect(0, 0, (WORLD_SIZE + 1) * xScale, (WORLD_SIZE + 1) * yScale);
            world.drawPlants(worldScreen);
            world.drawAnimals(worldScreen);

            worldScreen.camera(
                    xCoord,
                    yCoord,
                    (WORLD_SIZE * xScale / (float)Math.exp(this.zoom)),
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
        noStroke();
        fill(229,221,203);
        rect(width - statBarWidth + statBarBoarderWidth, 0, 120, height);
        fill(167,197,189);
        rect(width - statBarWidth, 0, statBarBoarderWidth, height);
        fill(82,70,86);
        text("Day: #" + day, width - 110, 20);
        text("Avg. age: " + world.avarageLifeTime() +
                "s,\nAvg. energy: " + world.avarageEnergy() , width - 110, 60);
        plot.drawPlot(width - statBarWidth + statLeftPadding + statBarBoarderWidth, height - 30);
        day++;
        popCountPlot.drawPlot(width - statBarWidth + statLeftPadding + statBarBoarderWidth, height - 180);
        if (millis() > lastTimeCheck + timeIntervalFlag) {
            lastTimeCheck = millis();
            popCountPlot.updatePlot();
            plot.updateGensPop();
        }
        image(worldScreen, 0, 0);
    }

    public void mouseClicked() {
        if (mouseButton == LEFT) {
            world.addJungle((int) ((float)WORLD_SIZE * ((float)mouseX / (float)(width - statBarWidth))),
                    (int) ((float)WORLD_SIZE * ((float)mouseY / (float)height)));
        }
        if (mouseButton == RIGHT) {
            world.addAnimal((int) ((float)WORLD_SIZE * ((float)mouseX / (float)(width - statBarWidth))),
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
