import java.util.*;

/**
 * Automate
 *
 */

public class Automate {
    public static void main (String[] args) {
         // création de lettres
         Letter letter1 = new Letter("a");
         Letter letter2 = new Letter("b");
         Letter letter3 = new Letter("c");

         // création d'états
         State state1 = new State("q_1");
         State state2 = new State("q_2");
         State state3 = new State("q_3");

         // Création d'un objet de type States
         States<State> Q = new States<State>();
         Q.addState(state1);
         Q.addState(state2);
         Q.addState(state3);

//         System.out.println(Q.toString());


         //création des transitions
         Transition t1 = new Transition(state1,letter1,state1);
         Transition t2 = new Transition(state1,letter1,state2);
         Transition t3 = new Transition(state1,letter2,state3);
         Transition t4 = new Transition(state3,letter2,state3);
         Transition t5 = new Transition(state3,letter1,state2);

         //création de l'ensemble des  transitions
         Transitions<State> Delta = new Transitions<State>();
         Delta.addTransition(t1);
         Delta.addTransition(t2);
         Delta.addTransition(t3);
         Delta.addTransition(t4);
         Delta.addTransition(t5);

//        //affiche les successeurs par a de l'état q_1
//        States E1 = Delta.successor(state1,letter1);
//        System.out.println(E1.toString());
//
//        //affiche les successeurs par a de l'état q_2
//        States E2 = Delta.successor(state2,letter1);
//        System.out.println(E2.toString());
//
//        //affiche les successeurs par a de l'état q_3
//        States E3 = Delta.successor(state3,letter1);
//        System.out.println(E3.toString());
//
//        //affiche les successeurs par b de {q_1, q_2}
//        HashSet<State> H = new HashSet<State>();
//        H.add(state1);
//        H.add(state2);
//        States<State> S1 = new States<State>(H);
//        States E4 = Delta.successors(S1,letter2);
//        System.out.println(S1.toString());
//        System.out.println(E4.toString());


        HashSet<Letter> alphabet = new HashSet<>(2);
        alphabet.add(letter1);
        alphabet.add(letter2);

        States<State> SetOfInitialStates = new States<State>();
        SetOfInitialStates.addState(state1);

        States<State> SetOfFinalStates = new States<>();
        SetOfFinalStates.addState(state3);

        AFN<State> afn = new AFN<>(alphabet, Q, SetOfInitialStates, SetOfFinalStates, Delta);
        ArrayList<Letter> w = new ArrayList<>(1);
//        w.add(letter2);
//        w.add(letter2);
        w.add(letter1);
        for (int i = 0; i < 100; i++) {
            w.add(letter1);
        }
        w.add(letter2);
        Word word = new Word(w);
        System.out.println("Recognize " + word + " " + afn.Recognize(word));
        System.out.println("Has empty language " + afn.EmptyLanguage());
        System.out.println("Is deteministic " + afn.isDeterministic());
        System.out.println("Is complete " + afn.isComplete());
        afn.Complete();
        System.out.println("Is complete " + afn.isComplete());
        AFN<State> afnMirror = afn.Mirror();
    }
}
