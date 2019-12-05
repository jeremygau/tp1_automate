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
        Iterator<S> InitialStates = this.SetOfInitialStates.iterator();
        while (InitialStates.hasNext()) { //pour chaque etat initial
            S state = InitialStates.next();
            for (int i = 0; i < w.getContain().size(); i++) {
                States<S> successors = TransitionRelation.successor(state, w.getContain().get(i));
                if (successors.getSetofStates().isEmpty()) {
                    return false;
                }
                Iterator<S> newStates = successors.iterator();
                state = newStates.next();
            }
        }
        return true;
    }
}
