import java.util.*;

/**
 * classe Word .
 */
public class Word {

    private ArrayList<Letter> contain;

    /**
     * Constructeur d'objets de classe Word
     */
    public Word(ArrayList<Letter> contain) {
        this.contain = contain;
    }

    /**
     * accesseur
     */
    public ArrayList<Letter> getContain() {
        return this.contain;
    }

    Iterator<Letter> iterator() {
        return contain.iterator();
    }
}