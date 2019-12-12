import java.util.*;

/**
 * classe States 
 *
 */
public class States<S> implements Iterable<S>
{
    private HashSet<S> SetofStates; 

    /**
     * Constructeur d'objets de classe States
     * vide
     */
    public States()
    {
        this.SetofStates = new  HashSet<S>(); 
    }
    
    
    // eventuellement virer la méthode suivante
    /**
     * Constructeur d'objets de classe States
     * a partir d'un ensemble d'états de type S
     */
    public States(HashSet<S> Q)
    {
        // initialisation des variables d'instance
        this.SetofStates = Q;
    }

    /**
     * accesseur pour SetofStates
     */
    public HashSet<S> getSetofStates()
    {
        return this.SetofStates;
    }
    
    /**
     * Ajout d'un état 
     */
    public void addState(S s)
    {
      this.SetofStates.add(s);   
    }

    /**
     * Enlevement d'un état
     */
    public void removeState(S s) {
        this.SetofStates.remove(s);
    }
    
    /**
     * Union de deux States 
     */
    public void addAllStates(States set)
    {
     this.SetofStates.addAll(set.getSetofStates());    
    }
    
    
    /**
     * iterateur pour le type States
     */
    public Iterator<S> iterator() 
    {
     return this.SetofStates.iterator();       
    }
    
    
    /**
     * representation en String d'un ensemble d'états 
     */
    public String toString()     {
         Iterator<S> AllStates = this.SetofStates.iterator();
         String Output = "[ ";

         while (AllStates.hasNext()) {
               S etat = AllStates.next();
               Output = Output + etat.toString()+ " ";
         }
         Output = Output+"]";

         return Output;
    }

    /**
     * renvoie vrai si tous les états de l'ensemble d'états sont contenus dans cet ensemble d'états
     */
    public boolean containsAll(States<S> states) {
        for (S state : states) {
            if (!getSetofStates().contains(state)) {
                return false;
            }
        }
        return true;
    }
    
    
    
}
