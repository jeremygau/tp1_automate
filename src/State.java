import java.util.Objects;

/**
 * classe State
 *
 */
public class State
{
    private String name;

    /**
     * Constructeur d'objets de classe State
     */
    public State(String name)
    {
        // initialisation des variables d'instance
        this.name = name;
    }

    /**
     * accesseurs
     */
    public String getName()
    {
        return this.name;
    }
    
    public String toString()
    {
        return this.name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
