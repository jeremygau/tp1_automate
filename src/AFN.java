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

    public boolean isFinal(S state) {
        Iterator<S> finalStates = getSetOfFinalStates().iterator();
        while (finalStates.hasNext()) {
            if (finalStates.next().equals(state)) {
                return true;
            }
        }
        return false;
    }

    public boolean containFinal(States<S> states) {
        Iterator<S> finalStates = states.iterator();
        while (finalStates.hasNext()) {
            if (isFinal(finalStates.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean EmptyLanguage() {
        if (getSetOfInitialStates().getSetofStates().isEmpty()) return true;
        if (getSetOfFinalStates().getSetofStates().isEmpty()) return true;

        States<S> states = getSetOfInitialStates();
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

    public void Complete() {
        if (!isComplete()) {
            S bin = (S)new State("bin");
            SetOfStates.addState(bin);
            Iterator<S> iterator = SetOfStates.iterator();
            while (iterator.hasNext()) {
                S state = iterator.next();
                for (Letter letter : getAlphabet()) {
                    if (getTransitionRelation().successor(state, letter).getSetofStates().isEmpty()) {
                        TransitionRelation.addTransition(new Transition(state,letter,bin));
                    }
                }
            }
        }
    }
}
