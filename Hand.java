import java.util.*;

public class Hand extends Deck{

  private ArrayList<Card> hand = new ArrayList<Card>(2);
  private boolean owner;
  
  public Hand(boolean owner) {
    this.owner = owner;
  }

  public int getTotal(boolean withAce) {
    int total = 0;
    for (int i = 0; i < hand.size(); i++) {
      total += hand.get(i).getValue(hand.get(i).toString());
    }
    return total;
  }
  
  public String readHand() {

    String output = "";
    output += "  ";

    for (int i = 0; i < hand.size() ; i++) {
      if (hand.get(i).toString() != null) {
        output += "[" + hand.get(i).toString() + "] ";
      } else {
        output += "     ";
      }
    }
    output += " total: " + getTotal(true);
    return output;
  }

  boolean getOwner() {
    return this.owner;
  }
  
}