import com.sun.org.apache.bcel.internal.generic.POP;
import processing.core.PApplet;

/**
 * Created by szpirala on 20.05.17.
 */
public class PopulationPlot {

    private World world;
    private PApplet context;
    private int width;
    private int height;
    private int[] populationIntime;
    private int sampleCount;

    public PopulationPlot(World world, PApplet context, int width, int height, int sampleCount) {
        this.world = world;
        this.context = context;
        this.width = width;
        this.height = height;
        this.sampleCount = sampleCount;
        this.populationIntime = new int[sampleCount];
    }

    public void drawPlot(float leftBottomX, float leftBottomY) {
        int wid = (int) width / sampleCount;
        int max = 1;
        for (int i = 0; i < sampleCount; i++) {
            max = Math.max(max, populationIntime[i]);
        }
        context.fill(235,123,89);
        context.rect(leftBottomX, leftBottomY - 25 - height, width, height + 25);

        context.beginShape();
        context.fill(82,70,86);
        context.vertex(leftBottomX + (sampleCount-1) * wid, leftBottomY);
        context.vertex(leftBottomX, leftBottomY);
        for (int i = 0; i < sampleCount; i++) {
//            context.ellipse(leftBottomX + (i + 1) * wid, leftBottomY - populationIntime[i]*height/max, 5, 5);
            context.vertex(leftBottomX + i * wid, leftBottomY - populationIntime[i] * height / max);
        }
        context.endShape();
        context.fill(82,70,86);
        context.text("Population: " + populationIntime[populationIntime.length - 1], leftBottomX, leftBottomY + 15);

    }

    public void updatePlot(){
        int pop = world.getCount();
        for (int i = 1; i < populationIntime.length; i++) {
            populationIntime[i - 1] = populationIntime[i];
        }
        populationIntime[sampleCount - 1] = pop;
    }
}
