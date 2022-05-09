import java.util.*;

public class Deck{
  
  static final String[] SUITES    = {"♠","♥","♦","♣"};
  static final String[] SPECIALS  = {"A","J","Q","K"};
  static final int      BASE      = 9;
  static final int      SIZE      = 52;

  private ArrayList<Card> deck = new ArrayList<Card>(52);
  private ArrayList<Integer> IDs = new ArrayList<Integer>(52);
  
  public Deck(){
    for (int i = 0; i < SIZE; i++) {
      IDs.add(i);
    }

    for (int i : IDs) {
      deck.add(new Card(i));
    }
  }
}