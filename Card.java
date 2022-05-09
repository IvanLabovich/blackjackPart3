public class Card extends Deck{
  
  public String suit;
  public String value;
  boolean color;
  int id;
  String card;
  
  public Card(int seed) {
    int n = seed % (BASE + SPECIALS.length);
    int s = seed / (BASE + SUITES.length);
  
    String val = (n == 0) ? SPECIALS[n] : "" + (n + 1);
    if (n > BASE) val = SPECIALS[n - BASE];
  
    this.suit = SUITES[s];
    this.value = val;
    this.id = seed;
    
    this.color = (this.suit.equals(SPECIALS[3]) ||         
    this.suit.equals(SPECIALS[0])) ? false : true;
    
  }

  public String toString() {

    return this.suit + this.value;
    
  }

  public int getValue(String card) {
    String value = card.substring(1);
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return (value.equals("A")) ? 1 : 10;
    }
  }
  
}