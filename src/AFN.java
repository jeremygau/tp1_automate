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

    public boolean Recognize(Word w) {
        States<S> states = getSetOfInitialStates();
        for (int i = 0; i < w.getContain().size(); i++) {
            states = getTransitionRelation().successors(states, w.getContain().get(i));
            System.out.println(states);
            if (states.getSetofStates().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean EmptyLanguage() {
        for (Letter letter : getAlphabet()) {
            if (!getTransitionRelation().successors(getSetOfInitialStates(), letter).getSetofStates().isEmpty()) {
                return false;
            }
        }
        return true;
    }

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
}
