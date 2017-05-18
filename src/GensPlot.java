import processing.core.PApplet;

/**
 * Created by szpirala on 18.05.17.
 */
public class GensPlot {

    /**
     * Klasa ma odpowiadać za rysowanie wykresu średniej częstości danego genu.
     * UWAGA -- obliczanie tej częstości planuje zrobić gdzie indziej, przynajmniej narazie
     * gensPopulation -  tablica średniej częstości genów
     */

    private int[] gensPopulation;
    private PApplet context;
    private int width;
    private int height;

    public GensPlot(PApplet context, int width, int height){
//        this.gensPopulation = gensPopulation;
        this.context = context;
        this.width = width;
        this.height = height;
    }

    public void drawPlot(){
        float wid = (this.width - 10)/gensPopulation.length;
            float color = context.random(255);
        for (int i = 0; i < gensPopulation.length; i++) {
            context.fill(0, color * (i + 1), 0);
            context.rect(5 + i * wid, this.width - gensPopulation[i], wid, gensPopulation[i]);
        }
    }

    public void setGensPopulation(int[] gensPopulation) {
        this.gensPopulation = gensPopulation;
    }
}
