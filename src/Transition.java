import java.util.*;

/**
 * classe Transition
 *
 */
public class Transition<S> 
{
    private S source;
    private S target;
    private Letter label;
    
    /**
     * Constructeur d'objets de classe Transition
     */
    public Transition(S source, Letter a, S target)
    {
        this.source = source;
        this.target = target;
        this.label = a;
    }
    
    // accesseurs des attributs de la classe
    public S getSource()
    { return this.source;}
    
    public S getTarget()
    { return this.target;}
    
    public Letter getLabel()
    {return this.label;}

    @Override
    public String toString() {
        return "[" + source + ", " + label + ", " +  target + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition<?> that = (Transition<?>) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, label);
    }
}
