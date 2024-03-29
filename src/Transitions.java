import java.util.*;

/**
 * classe Transitions
 *
 */
public class Transitions<S>
{
    private Set<Transition<S>> SetofTransitions;
    
    /**
     * Constructeur d'objets de classe Transitions initialement vide
     */
    public Transitions()
    {
      this.SetofTransitions = new HashSet<Transition<S>>();
    }
    
    /**
     * Constructeur d'objets de classe Transitions à partir d'un ensemble de transitions
     */
    public Transitions(Set<Transition<S>> T)
    {
      this.SetofTransitions = T;  
    }

    
    /**
     * Ajout d'une transition 
     */
    public void addTransition(Transition<S> t)
    {
      this.SetofTransitions.add(t);   
    }

    /**
     * Enlevement d'une transition
     */
    public void removeTransition(Transition<S> t) {
        this.SetofTransitions.remove(t);
    }
    
    /**
     * Retourne l'ensemble des transitions représenté  
     */
    public Set<Transition<S>> getSetofTransitions()
    {
      return this.SetofTransitions;   
    }
    
    /**
     * Successeurs de l'état s et de la lettre a par les transitions
     */
    States<S> successor(S s,Letter a) {
        States<S> Targets = new States<S>();
        for (Transition<S> transition : SetofTransitions) {
            if (transition.getLabel() == a && transition.getSource() == s) {
                Targets.addState(transition.getTarget());
            }
        }
        return Targets;
    }
    
 /**
     * successeurs de l'ensemble d'états S et de la lettre a par les 
     * transitions
     */  
    States<S> successors(States<S> set,Letter a)
    {
        States<S> Targets = new States<S>();
        for (S state : set.getSetofStates()) {
            Targets.addAllStates(successor(state, a));
        }
        return Targets;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[");
        for (Transition<S> transition : SetofTransitions) {
            string.append(transition);
        }
        string.append("]");
        return string.toString();
    }
}
