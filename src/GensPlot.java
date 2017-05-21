import processing.core.PApplet;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by szpirala on 18.05.17.
 */
public class GensPlot {

    /**
     * Klasa ma odpowiadać za rysowanie wykresu średniej częstości danego genu.
     * UWAGA -- obliczanie tej częstości planuje zrobić gdzie indziej, przynajmniej narazie
     * gensPopulation -  tablica średniej częstości genów
     */

    private float[] gensPopulation;
    private PApplet context;
    private World world;
    private int width;
    private int height;

    public GensPlot(World world, int width, int height){
        this.gensPopulation = new float[8];
        this.world = world;
        this.context = world.getContext();
        this.width = width;
        this.height = height;
    }

    public void drawPlot(float leftBottomX, float leftBottomY){
//        this.gensPopulation = world.getGensData();
        float max = 1;
        for (int i = 0; i < gensPopulation.length; i++) {
            max = Math.max(gensPopulation[i], max);
        }
        float wid = (this.width - 10)/gensPopulation.length;
            float color = 30;
        for (int i = 0; i < gensPopulation.length; i++) {
            context.fill(0, color * (i + 1), 0);
            context.rect(leftBottomX + i * wid, leftBottomY, wid, -gensPopulation[i]* height/ max );
            context.text( i+1, leftBottomX + i * wid, leftBottomY + 10);
        }
    }

    public void updateGensPop(){
        this.gensPopulation = world.getGensData();
    }

    public void setGensPopulation(float[] gensPopulation) {
        for (int i = 0; i < gensPopulation.length; i++) {
            gensPopulation[i] = gensPopulation[i];
        }
        this.gensPopulation = gensPopulation;
    }
}
