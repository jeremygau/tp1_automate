import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class AFN<S> {

    HashSet<Letter> Alphabet;
    States<S> SetOfStates;
    States<S> SetOfInitialStates;
    States<S> SetOfFinalStates;
    Transitions<S> TransitionRelation;

    public AFN(HashSet<Letter> alphabet, States<S> setOfStates, States<S> setOfInitialStates, States<S> setOfFinalStates, Transitions<S> transitionRelation) {
        Alphabet = alphabet;
        SetOfStates = setOfStates;
        SetOfInitialStates = setOfInitialStates;
        SetOfFinalStates = setOfFinalStates;
        TransitionRelation = transitionRelation;
    }

    /**
     * accesseurs
     */
    public HashSet<Letter> getAlphabet() {
        return Alphabet;
    }

    public States<S> getSetOfStates() {
        return SetOfStates;
    }

    public States<S> getSetOfInitialStates() {
        return SetOfInitialStates;
    }

    public States<S> getSetOfFinalStates() {
        return SetOfFinalStates;
    }

    public Transitions<S> getTransitionRelation() {
        return TransitionRelation;
    }

    /**
     * retourne vrai si le mot donné en parametre est dans le langage défini par l’automate et faux sinon
     */
    public boolean Recognize(Word w) {
        States<S> states = getSetOfInitialStates();
        for (int i = 0; i < w.getContain().size(); i++) {
            states = getTransitionRelation().successors(states, w.getContain().get(i));
            if (states.getSetofStates().isEmpty()) {
                return false;
            }
        }
        Iterator<S> finalStates = states.iterator();
        while (finalStates.hasNext()) {
            if (isFinal(finalStates.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * renvoie vrai si l'etat donné en parametre est final, faux sinon
     */
    public boolean isFinal(S state) {
        Iterator<S> finalStates = getSetOfFinalStates().iterator();
        while (finalStates.hasNext()) {
            if (finalStates.next().equals(state)) {
                return true;
            }
        }
        return false;
    }

    /**
     * revoie vrai si l'ensemble d'etats donné en parametre contient au moins 1 etats final, faux sinon
     */
    public boolean containFinal(States<S> states) {
        Iterator<S> finalStates = states.iterator();
        while (finalStates.hasNext()) {
            if (isFinal(finalStates.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * teste si le langage défini par l’automateest vide ou non
     */
    public boolean EmptyLanguage() {
        if (getSetOfInitialStates().getSetofStates().isEmpty()) return true;
        if (getSetOfFinalStates().getSetofStates().isEmpty()) return true;

        States<S> states = new States<>();
        states.addAllStates(getSetOfInitialStates());
        while (!containFinal(states)) {
            for (Letter letter : getAlphabet()) {
                if (getTransitionRelation().successors(states, letter).getSetofStates().isEmpty()) {
                    return true;
                }
                states.addAllStates(getTransitionRelation().successors(states, letter));
            }
        }
        return false;
    }

    /**
     * teste si l’automate est déterministe
     */
    public boolean isDeterministic() {
        for (S state : getSetOfStates().getSetofStates()) {
            for (Letter letter : getAlphabet()) {
                if (getTransitionRelation().successor(state, letter).getSetofStates().size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * teste si l’automate est complet
     */
    public boolean isComplete() {
        for (S state : getSetOfStates().getSetofStates()) {
            for (Letter letter : getAlphabet()) {
                if (getTransitionRelation().successor(state, letter).getSetofStates().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * complète si nécessaire l’automate
     */
    public void Complete() {
        if (!isComplete()) {
            S bin = (S)new State("bin");
            SetOfStates.addState(bin);
            for(S state : SetOfStates) {
                for (Letter letter : getAlphabet()) {
                    if (getTransitionRelation().successor(state, letter).getSetofStates().isEmpty()) {
                        TransitionRelation.addTransition(new Transition(state,letter,bin));
                    }
                }
            }
        }
    }

    /**
     * calcule le miroir de l’automate
     */
    public AFN<S> Mirror() {
        Transitions<S> transitions = new Transitions<>();
        for (Transition<S> cur : getTransitionRelation().getSetofTransitions()) {
            Transition<S> transition = new Transition<>(cur.getTarget(), cur.getLabel(), cur.getSource());
            transitions.addTransition(transition);
        }
        return new AFN<S>(getAlphabet(), getSetOfStates(), getSetOfFinalStates(), getSetOfInitialStates(), transitions);
    }

    /**
     * retourne l’ensemble des états accessibles de l'automate
     */
    public States<S> Reachable() {
        States<S> reachablesStates = new States<>();
        if (getSetOfInitialStates().getSetofStates().isEmpty()) return reachablesStates;

        reachablesStates.addAllStates(getSetOfInitialStates());
        States<S> temporareStates = getSetOfInitialStates();
        boolean continuer = true;
        while (continuer){
            continuer = false;
            for (Letter letter : getAlphabet()) {
                temporareStates = getTransitionRelation().successors(reachablesStates, letter);
                if(!reachablesStates.containsAll(temporareStates))
                    continuer = true;
                reachablesStates.addAllStates(temporareStates);
            }
        }
        return reachablesStates;
    }

    /**
     * retourne l’ensemble des états co-accessibles de l'automate
     */
    public States<S> Coreachable() {
        return this.Mirror().Reachable();
    }

    /**
     * élimine de l’automate les états non accessibles ou non co-accessibles ainsi que les transitions les utilisant.
     */
    public void Trim() {
        States<S> uselessStates = Reachable();
        uselessStates.addAllStates(Coreachable());
        for (S uselessState : uselessStates) {
            SetOfStates.removeState(uselessState);
            for (Transition<S> transition : getTransitionRelation().getSetofTransitions()) {
                if (transition.getSource() == uselessState || transition.getLabel() == uselessState) {
                    TransitionRelation.removeTransition(transition);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "AFN{" +
                "Alphabet=" + Alphabet + "\n" +
                "SetOfStates=" + SetOfStates + "\n" +
                "SetOfInitialStates=" + SetOfInitialStates +" \n" +
                "SetOfFinalStates=" + SetOfFinalStates + "\n" +
                "TransitionRelation=\n" + TransitionRelation +
                "}\n";
    }
}
