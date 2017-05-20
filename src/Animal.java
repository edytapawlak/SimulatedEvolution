/**
 * Created by szpirala on 18.05.17.
 */
public class Animal {
    private int age;
    private Animal[] chilrens;
    /**
     * 8 genów odpowiadających za kierunek i szybkość
     * 9ty gen określającą zdolność rozmnażania -- liczba dzieci
     */
    private int[] gens;


    public Animal(int[] gens){
        this.gens = gens;
    }

    public void reproduce(){
        this.chilrens = new Animal[gens.length - 1];
    }
}
